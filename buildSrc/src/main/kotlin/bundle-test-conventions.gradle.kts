plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

val jsTestBundle = configurations.create("jsTestBundle")

val bundleProjects = providers.provider {
    jsTestBundle
        .allDependencies
        .asSequence()
        .filterIsInstance<ProjectDependency>()
        .map { it.dependencyProject }
        .toSet()
}

val unpackBundle by tasks.registering(Sync::class) {
    from(
        bundleProjects.get()
            .map { it.tasks.named<Jar>("jsBundleProduction") }
            .map { it.map { task -> zipTree(task.archiveFile) } }
    )

    into(temporaryDir)
}

tasks.named<Test>("jvmTest") {
    dependsOn(unpackBundle)

    environment("BUNDLE_PATH", unpackBundle.get().destinationDir)
}
