package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

private const val YARN_VERSION = "1.22.19"
private const val NODE_VERSION = "18.10.0"

class LatestNodePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.configureLatestNode()
    }
}

private fun Project.configureLatestNode() {
    plugins.withType<YarnPlugin> {
        the<YarnRootExtension>().apply {
            version = YARN_VERSION
            lockFileDirectory = projectDir
            ignoreScripts = false
        }
    }

    plugins.withType<NodeJsRootPlugin> {
        the<NodeJsRootExtension>().apply {
            nodeVersion = NODE_VERSION
        }
    }
}
