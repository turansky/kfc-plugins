plugins {
    id("io.github.turansky.kfc.application")
    id("io.github.turansky.kfc.wrappers")
}

fun Project.coroutines(name: String): String =
    kotlinx("kotlinx-coroutines", name)

private fun Project.kotlinx(projectName: String, name: String): String =
    "org.jetbrains.kotlinx:$projectName-$name:${property("$projectName.version")}"

dependencies {
    jsMainImplementation(wrappers("js"))

    jsMainImplementation(project(":examples:resources:lib-a"))
    jsMainImplementation(project(":examples:resources:lib-b"))
    jsMainImplementation(project(":examples:resources:lib-c"))

    jsTestImplementation(kotlin("test-js"))
    jsTestImplementation(coroutines("core"))
    jsTestImplementation(coroutines("test"))
}
