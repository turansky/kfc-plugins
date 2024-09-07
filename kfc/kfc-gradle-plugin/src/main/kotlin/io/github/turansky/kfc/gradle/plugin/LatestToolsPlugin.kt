package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.LockFileMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmExtension

private const val NODE_VERSION = "22.7.0"

private const val YARN = "kotlin.js.yarn"

class LatestToolsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.plugins.apply(RootLatestToolsPlugin::class)
    }
}

private class RootLatestToolsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        ext(YARN, false)

        plugins.withType<NodeJsRootPlugin> {
            the<NodeJsRootExtension>().apply {
                version = NODE_VERSION

                versions.apply {
                    webpack.version = "5.94.0"
                    webpackCli.version = "5.1.4"
                    webpackDevServer.version = "5.1.0"
                    karma.version = "6.4.4"
                }
            }

            the<NpmExtension>().apply {
                lockFileDirectory.set(projectDir)
                packageLockMismatchReport.set(LockFileMismatchReport.NONE)
                packageLockAutoReplace.set(true)
            }
        }
    }
}
