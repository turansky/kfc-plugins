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

val unpackBundle by tasks.creating {
    doFirst {
        val bundles = configurations.getByName(JS_TEST_BUNDLE)
            .allDependencies
            .asSequence()
            .filterIsInstance<ProjectDependency>()
            .map { it.dependencyProject }
            .toSet()

        bundles.forEach { bundle ->
            println("Unpacking bundle ${bundle.name}")
        }
    }
}

tasks.named("jvmTest") {
    dependsOn(unpackBundle)
}
