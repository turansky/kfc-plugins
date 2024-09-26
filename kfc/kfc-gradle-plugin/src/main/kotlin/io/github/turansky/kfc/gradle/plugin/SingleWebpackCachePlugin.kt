package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

class SingleWebpackCachePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val prodOutput = getProductionDistDirectory()
        val devOutput = getDevelopmentDistDirectory()

        val deleteRelatedOutputProd by tasks.creating(Delete::class) {
            delete(devOutput)
        }

        val deleteRelatedOutputDev by tasks.creating(Delete::class) {
            delete(prodOutput)
        }

        tasks.named<KotlinWebpack>(Webpack.productionTask) {
            dependsOn(deleteRelatedOutputProd)
            outputDirectory.set(prodOutput)
        }

        tasks.named<KotlinWebpack>(Webpack.developmentTask) {
            dependsOn(deleteRelatedOutputDev)
            outputDirectory.set(devOutput)
        }
    }
}
