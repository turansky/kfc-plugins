package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.register
import java.util.*

class BundlePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        if (kfcPlatform.js) {
            addBundleTasks(Vite.js)
        }

        if (kfcPlatform.wasmJs) {
            addBundleTasks(Vite.wasmJs)
        }
    }

    private fun Project.addBundleTasks(
        configuration: BundlerConfiguration,
    ) {
        val platform = configuration.platform
        val platformId = platform.lowercase(Locale.US)

        tasks.register<Jar>("${platform}BundleProduction") {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("${platformId}-bundle-production")

            from(tasks.named(configuration.production.buildTask))
        }

        tasks.register<Jar>("${platform}BundleDevelopment") {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("${platformId}-bundle-development")

            from(tasks.named(configuration.development.buildTask))
        }
    }
}
