package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

class LatestWebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.configureLatestWebpack()
    }
}

private fun Project.configureLatestWebpack() {
    plugins.withType<NodeJsRootPlugin> {
        the<NodeJsRootExtension>().versions.apply {
            webpack.version = "5.72.0"
            webpackCli.version = "4.9.2"
            webpackDevServer.version = "4.8.1"

            karma.version = "6.3.18"
        }
    }
}
