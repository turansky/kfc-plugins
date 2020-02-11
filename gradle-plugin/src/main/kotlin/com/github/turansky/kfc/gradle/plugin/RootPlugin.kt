package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

private val NODE_DOWNLOAD_BASE_URL = "kfc.node.download.base.url"
private val YARN_DOWNLOAD_BASE_URL = "kfc.yarn.download.base.url"

class RootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configureProject()
    }
}

private fun Project.configureProject() {
    if (hasProperty(NODE_DOWNLOAD_BASE_URL)) {
        plugins.withType<NodeJsRootPlugin> {
            configure<NodeJsRootExtension> {
                nodeDownloadBaseUrl = property(NODE_DOWNLOAD_BASE_URL) as String
            }
        }
    }

    if (hasProperty(YARN_DOWNLOAD_BASE_URL)) {
        yarn.downloadBaseUrl = property(YARN_DOWNLOAD_BASE_URL) as String
    }
}
