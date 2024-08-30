package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.Bundler.Vite
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Sync
import org.gradle.kotlin.dsl.create

class ViteApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.create(Vite.developmentTask)

        tasks.create<Sync>(Vite.productionTask) {
            from(tasks.named(COMPILE_PRODUCTION)) {
                rename("(.+).mjs", "$1.js")
            }

            into(temporaryDir)
        }
    }
}
