import org.gradle.api.Project

fun Project.ktor(target: String): String {
    val version = property("ktor.version") as String
    return "io.ktor:ktor-$target:$version"
}

val logbackVersion = "1.2.3"
