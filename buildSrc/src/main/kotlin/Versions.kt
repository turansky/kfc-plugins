import org.gradle.api.Project

val Project.ktorVersion: String
    get() = property("ktor.version") as String

val logbackVersion = "1.2.3"
