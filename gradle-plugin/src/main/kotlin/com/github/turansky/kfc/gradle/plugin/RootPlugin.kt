package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

private val DOWNLOAD_BASE_URL = "kfc.download.base.url"

private val NODE_PATH = "nodejs.org/dist"
private val YARN_PATH = "yarnpkg/yarn"

private val NODE_DOWNLOAD_BASE_URL = "kfc.node.download.base.url"
private val YARN_DOWNLOAD_BASE_URL = "kfc.yarn.download.base.url"

class RootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        nodeDownloadBaseUrl()?.let { baseUrl ->
            plugins.withType<NodeJsRootPlugin> {
                configure<NodeJsRootExtension> {
                    nodeDownloadBaseUrl = baseUrl
                }
            }
        }

        yarnDownloadBaseUrl()?.let { baseUrl ->
            yarn.downloadBaseUrl = baseUrl
        }
    }
}

private fun Project.nodeDownloadBaseUrl(): String? =
    propertyOrNull(NODE_DOWNLOAD_BASE_URL)
        ?: downloadBaseUrl(NODE_PATH)

private fun Project.yarnDownloadBaseUrl(): String? =
    propertyOrNull(YARN_DOWNLOAD_BASE_URL)
        ?: downloadBaseUrl(YARN_PATH)

private fun Project.downloadBaseUrl(suffix: String): String? =
    propertyOrNull(DOWNLOAD_BASE_URL)
        ?.let { "$it/$suffix" }

private fun Project.propertyOrNull(s: String): String? =
    if (hasProperty(s)) property(s) as String else null
