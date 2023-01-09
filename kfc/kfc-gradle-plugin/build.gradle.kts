plugins {
    `kotlin-dsl`

    id("com.gradle.plugin-publish")
    id("com.github.turansky.kfc.plugin-publish")
}

dependencies {
    implementation("nu.studer:java-ordered-properties:1.0.4")
    compileOnly(kotlin("gradle-plugin"))
}

// TODO: remove after Gradle update
tasks.compileKotlin {
    kotlinOptions.allWarningsAsErrors = false
}

val REPO_URL = "https://github.com/turansky/kfc-plugins"

enum class KfcPlugin(
    val displayName: String,
    val description: String,
    tags: Collection<String>,
) {
    ROOT(
        displayName = "Root plugin",
        description = "Configure Kotlin/JS plugin in root project",
        tags = listOf("root"),
    ),

    VERSION(
        displayName = "Version plugin",
        description = "Provide version tasks in root project",
        tags = listOf("version"),
    ),

    NPM_BUILD(
        displayName = "npm build plugin",
        description = "Predefined npm tasks for Kotlin/JS",
        tags = listOf("npm"),
    ),

    MAVEN_PUBLISH(
        displayName = "Maven publish plugin",
        description = "Predefined maven publications for Kotlin",
        tags = listOf("maven", "publish"),
    ),

    MULTIPLATFORM(
        displayName = "Kotlin multiplatform library plugin",
        description = "Optimize Kotlin multiplatform library configuration",
        tags = listOf("multiplatform", "library"),
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

    COMPONENT(
        displayName = "Kotlin/JS component plugin",
        description = "Kotlin/JS component configuration",
        tags = listOf("component"),
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

    DEV_SERVER(
        displayName = "Development server plugin",
        description = "Testing server for Kotlin/JS",
        tags = listOf("dev server", "dev testing"),
    ),

    WRAPPERS(
        displayName = "Wrappers plugin",
        description = "Kotlin Wrappers support for Kotlin/JS projects",
        tags = listOf("wrappers", "react"),
    ),

    REACT(
        displayName = "React plugin",
        description = "React support for Kotlin/JS projects",
        tags = listOf("react", "lazy", "display name"),
    ),

    DEFINITIONS(
        displayName = "Kotlin/JS definitions plugin",
        description = "Kotlin/JS definitions configuration",
        tags = listOf("definitions"),
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

    LEGACY_UNION(
        displayName = "Legacy union plugin",
        description = "Union compatibility plugin for Kotlin/JS legacy compiler",
        tags = listOf("webpack"),
    ),

    REACT_DATES(
        displayName = "react-dates plugin",
        description = "React 17+ compatibility fix for react-dates",
        tags = listOf("react", "react-dates"),
    ),

    ;

    val pluginName: String = name.toLowerCase().replace("_", "-")

    val id: String = "io.github.turansky.kfc.$pluginName"
    val implementationClass: String = run {
        val className = name.toLowerCase().replace(
            regex = Regex("\\_(\\w)"),
            transform = { it.groupValues[1].toUpperCase() },
        ).capitalize() + "Plugin"

        "io.github.turansky.kfc.gradle.plugin.$className"
    }

    val tags: List<String> = listOf(
        "kotlin",
        "kotlin-js",
        "javascript"
    ) + tags
}

gradlePlugin {
    plugins {
        for (kfcPlugin in KfcPlugin.values()) {
            create(kfcPlugin.pluginName) {
                id = kfcPlugin.id
                displayName = kfcPlugin.displayName
                description = kfcPlugin.description
                implementationClass = kfcPlugin.implementationClass
            }
        }
    }
}

pluginBundle {
    website = REPO_URL
    vcsUrl = REPO_URL

    pluginTags = KfcPlugin.values()
        .associate { it.pluginName to it.tags }
}
