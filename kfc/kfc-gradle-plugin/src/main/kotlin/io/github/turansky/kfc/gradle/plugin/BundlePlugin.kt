package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue

class BundlePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val jsBundleDevelopment by tasks.creating(Jar::class) {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("js-bundle-development")

            from(tasks.named(Webpack.DEVELOPMENT_TASK))
        }

        val jsBundleProduction by tasks.creating(Jar::class) {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("js-bundle-production")

            from(tasks.named(Webpack.PRODUCTION_TASK))
        }
    }
}
