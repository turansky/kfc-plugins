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

val JS_TEST_BUNDLE = "jsTestBundle"

configurations.create(JS_TEST_BUNDLE)

val bundlesProvider = providers.provider {
    configurations.getByName(JS_TEST_BUNDLE)
        .allDependencies
        .asSequence()
        .filterIsInstance<ProjectDependency>()
        .map { it.dependencyProject }
        .toSet()
}

val unpackBundle by tasks.registering(Copy::class) {
    val bundles = bundlesProvider.get()

    delete(temporaryDir)

    from(
        bundles.map { bundle ->
            bundle.tasks.named<Jar>("jsBundleProduction").map { it ->
                zipTree(it.archiveFile)
            }
        }
    )

    into(temporaryDir)
}

tasks.named<Test>("jvmTest") {
    dependsOn(unpackBundle)

    environment("TEST_BUNDLE", unpackBundle.get().destinationDir)
}
