package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.LockFileMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmExtension

private const val YARN = "kotlin.js.yarn"

class LatestToolsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(LatestNodePlugin::class)
        rootProject.plugins.apply(RootLatestToolsPlugin::class)
    }
}

class LatestNodePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<NodeJsPlugin> {
            the<NodeJsEnvSpec>().version.set("22.14.0")
        }
    }
}

private class RootLatestToolsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        ext(YARN, false)

        plugins.apply(LatestNodePlugin::class)

        plugins.withType<NodeJsRootPlugin> {
            the<NodeJsRootExtension>().versions.apply {
                webpack.version = "5.98.0"
                webpackCli.version = "6.0.1"
                karma.version = "6.4.4"
            }

            the<NpmExtension>().apply {
                lockFileDirectory.set(projectDir)
                packageLockMismatchReport.set(LockFileMismatchReport.NONE)
                packageLockAutoReplace.set(true)
            }
        }
    }
}
