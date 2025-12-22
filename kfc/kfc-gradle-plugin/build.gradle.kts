import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    `kotlin-dsl`

    id("com.gradle.plugin-publish")
    id("kfc-plugin-publish")
}

dependencies {
    implementation("nu.studer:java-ordered-properties:1.0.4")
    compileOnly(kotlin("gradle-plugin"))
}

val REPO_URL = "https://github.com/turansky/kfc-plugins"

enum class KfcPlugin(
    val displayName: String,
    val description: String,
    tags: Collection<String>,
) {
    VERSION(
        displayName = "Version plugin",
        description = "Provide version tasks in root project",
        tags = listOf("version"),
    ),

    LIBRARY(
        displayName = "Kotlin library plugin",
        description = "Optimize Kotlin library configuration",
        tags = listOf("library"),
    ),

    APPLICATION(
        displayName = "Kotlin/JS application plugin",
        description = "Kotlin/JS application configuration",
        tags = listOf("application"),
    ),

    MAVEN_CENTRAL_PUBLISH(
        displayName = "Maven central publish plugin",
        description = "Maven central publish configuration",
        tags = listOf("maven", "central", "publish"),
    ),

    PLUGIN_PUBLISH(
        displayName = "Plugin publish plugin",
        description = "Provide publish tasks for gradle plugin project",
        tags = listOf("publish"),
    ),

    ;

    val pluginName: String = name.lowercase().replace("_", "-")

    val id: String = "io.github.turansky.kfc.$pluginName"
    val implementationClass: String = run {
        val className = name.lowercase().replace(
            regex = Regex("\\_(\\w)"),
            transform = { it.groupValues[1].uppercase() },
        ).uppercaseFirstChar() + "Plugin"

        "io.github.turansky.kfc.gradle.plugin.$className"
    }

    val tags: List<String> = listOf(
        "kotlin",
        "kotlin-js",
        "javascript"
    ) + tags
}

gradlePlugin {
    website = REPO_URL
    vcsUrl = REPO_URL

    plugins {
        for (kfcPlugin in KfcPlugin.entries) {
            create(kfcPlugin.pluginName) {
                id = kfcPlugin.id
                displayName = kfcPlugin.displayName
                description = kfcPlugin.description
                implementationClass = kfcPlugin.implementationClass
                tags = kfcPlugin.tags
            }
        }
    }
}

// TODO: For suppression of "INVISIBLE_REFERENCE" of internal `npmProject.nodeExecutable`
//  Because invocation of the lazy delegate `npmProject.nodeJs` results in NPE
tasks.compileKotlin {
    compilerOptions.freeCompilerArgs.addAll(
        "-Xdont-warn-on-error-suppression",
    )
}
