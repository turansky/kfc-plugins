package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin

class LatestNodePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.configureLatestNode()
    }
}

private fun Project.configureLatestNode() {
    YarnPlugin.apply(this).version = "1.22.17"
    the<NodeJsRootExtension>().nodeVersion = "16.13.1"
}
