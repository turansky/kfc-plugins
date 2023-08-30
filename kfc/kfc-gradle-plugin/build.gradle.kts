import org.gradle.configurationcache.extensions.capitalized

plugins {
    `kotlin-dsl`

    id("com.gradle.plugin-publish")
    id("com.github.turansky.kfc.plugin-publish")
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

    WEBPACK(
        displayName = "Kotlin/JS webpack plugin",
        description = "Webpack configuration for Kotlin/JS",
        tags = listOf("webpack", "config"),
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

    WORKER(
        displayName = "Kotlin/JS worker plugin",
        description = "Kotlin/JS worker configuration",
        tags = listOf("worker"),
    ),

    WRAPPERS(
        displayName = "Wrappers plugin",
        description = "Kotlin Wrappers support for Kotlin/JS projects",
        tags = listOf("wrappers", "react"),
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

    LATEST_WEBPACK(
        displayName = "Latest webpack plugin",
        description = "Configure latest/stable webpack for Kotlin/JS projects",
        tags = listOf("webpack"),
    ),

    ;

    val pluginName: String = name.lowercase().replace("_", "-")

    val id: String = "io.github.turansky.kfc.$pluginName"
    val implementationClass: String = run {
        val className = name.lowercase().replace(
            regex = Regex("\\_(\\w)"),
            transform = { it.groupValues[1].uppercase() },
        ).capitalized() + "Plugin"

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
        for (kfcPlugin in KfcPlugin.values()) {
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
