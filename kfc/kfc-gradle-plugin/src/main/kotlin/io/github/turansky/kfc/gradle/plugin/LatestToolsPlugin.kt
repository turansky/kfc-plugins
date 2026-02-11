package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.NpmVersions
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.LockFileMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmExtension
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsPlugin
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.wasm.npm.WasmNpmExtension

private const val NODE_VERSION = "24.13.1"

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
            the<NodeJsEnvSpec>().version.set(NODE_VERSION)
        }

        plugins.withType<WasmNodeJsPlugin> {
            the<WasmNodeJsEnvSpec>().version.set(NODE_VERSION)
        }
    }
}

private class RootLatestToolsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        ext(YARN, false)

        plugins.apply(LatestNodePlugin::class)

        plugins.withType<NodeJsRootPlugin> {
            the<NodeJsRootExtension>().versions.configureVersions()

            the<NpmExtension>().apply {
                lockFileDirectory.set(projectDir)
                packageLockMismatchReport.set(LockFileMismatchReport.NONE)
                packageLockAutoReplace.set(true)
            }
        }

        plugins.withType<WasmNodeJsRootPlugin> {
            the<WasmNodeJsRootExtension>().versions.configureVersions()

            the<WasmNpmExtension>().apply {
                lockFileDirectory.set(project.layout.buildDirectory.file("wasm-package-lock").get().asFile)
                packageLockMismatchReport.set(LockFileMismatchReport.NONE)
            }
        }
    }
}

fun NpmVersions.configureVersions() {
    // https://www.npmjs.com/package/webpack
    webpack.version = "5.102.1"

    // https://www.npmjs.com/package/webpack-cli
    webpackCli.version = "6.0.1"

    // https://www.npmjs.com/package/webpack-dev-server
    webpackDevServer.version = "5.2.2"
}
