package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.BuildMode.WORKER
import org.gradle.api.Plugin
import org.gradle.api.Project

class WorkerPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin(WORKER)
    }
}
