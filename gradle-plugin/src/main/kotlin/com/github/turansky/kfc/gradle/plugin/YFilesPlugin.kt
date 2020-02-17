package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

private val EXTERNALS = """
config.externals = [
    /^yfiles${'$'}/i,
    /^yfiles\/.+${'$'}/i
]
""".trimIndent()

class YFilesPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.withType<WebpackConfigTask> {
            patch("externals", EXTERNALS)
        }
    }
}
