package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

private const val YARN_VERSION = "1.22.19"
private const val NODE_VERSION = "20.9.0"

class LatestNodePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.plugins.apply(RootLatestNodePlugin::class)
    }
}

private class RootLatestNodePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<YarnPlugin> {
            the<YarnRootExtension>().apply {
                version = YARN_VERSION
                lockFileDirectory = projectDir
                yarnLockMismatchReport = YarnLockMismatchReport.NONE
                yarnLockAutoReplace = true
                ignoreScripts = false
            }
        }

        plugins.withType<NodeJsRootPlugin> {
            the<NodeJsRootExtension>().apply {
                nodeVersion = NODE_VERSION
            }
        }
    }
}
