package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

class PluginVersionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks {
            register("preparePublish") {
                doLast {
                    // implement
                }
            }

            register("prepareDevelopment") {
                doLast {
                    // implement
                }
            }
        }
    }
}
