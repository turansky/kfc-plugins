package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        applyKotlinMultiplatformPlugin()
    }
}
