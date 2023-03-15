package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

private const val BPW: String = "jsBrowserProductionWebpack"
private const val BDW: String = "jsBrowserDevelopmentWebpack"

class SingleWebpackCachePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.named<KotlinWebpack>(BDW) {
            destinationDirectory = tasks.named<KotlinWebpack>(BPW).get().destinationDirectory
        }
    }
}
