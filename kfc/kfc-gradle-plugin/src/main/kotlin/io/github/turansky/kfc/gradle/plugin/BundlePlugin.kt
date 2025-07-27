package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.BuildMode.APPLICATION
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.register

class BundlePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val bundler = requireNotNull(APPLICATION.bundler)

        if (kfcPlatform.js) {
            addBundleTasks(bundler.js)
        }

        if (kfcPlatform.wasmJs) {
            addBundleTasks(bundler.wasmJs)
        }
    }

    private fun Project.addBundleTasks(
        configuration: BundlerConfiguration,
    ) {
        val platform = configuration.platform

        tasks.register<Jar>("${platform.name}BundleProduction") {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("${platform.id}-bundle-production")

            from(tasks.named(configuration.production.buildTask))
        }

        tasks.register<Jar>("${platform.name}BundleDevelopment") {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("${platform.id}-bundle-development")

            from(tasks.named(configuration.development.buildTask))
        }
    }
}
