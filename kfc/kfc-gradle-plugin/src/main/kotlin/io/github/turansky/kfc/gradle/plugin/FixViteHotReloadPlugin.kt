package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinPackageJsonTask

// Redundant event is thrown when Vite hot reloads, making it restart the whole thing
// The issue presents in `fsevents`: `2.3.2` and below
// Original issue: https://github.com/paulmillr/chokidar/issues/1286
class FixViteHotReloadPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinPackageJsonTask> {
            packageJsonHandlers.add {
                overrides = mapOf("fsevents" to "^2.3.3")
            }
        }
    }
}
