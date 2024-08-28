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
    bundleProjects.get()
        .map {
            val tasks = it.tasks.named<Jar>("jsBundleProduction")

            from(tasks.map { jar -> zipTree(jar.archiveFile) }) {
                into(it.name)
            }
        }

    into(temporaryDir)
}

tasks.named<Test>("jvmTest") {
    dependsOn(unpackBundle)

    environment("BUNDLE_PATH", unpackBundle.get().destinationDir)
}
