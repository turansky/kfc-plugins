package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue

class BundlePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val bundler = getBundler()

        val jsBundleProduction by tasks.creating(Jar::class) {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("js-bundle-production")

            from(tasks.named(bundler.productionTask)) {
                exclude(Webpack.configFile)
            }
        }

        val jsBundleDevelopment by tasks.creating(Jar::class) {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("js-bundle-development")

            from(tasks.named(bundler.developmentTask)) {
                exclude(Webpack.configFile)
            }
        }
    }
}
