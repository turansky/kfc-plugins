package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

private const val GRADLE_PLUGIN_PREFIX = "gradle.plugin."

class PluginVersionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks {
            register("preparePublish") {
                doLast {
                    changeVersion(currentVersion.toRelease())
                    changeGroup(addPrefix = false)
                }
            }

            register("prepareDevelopment") {
                doLast {
                    changeVersion(currentVersion.toNextSnapshot())
                    changeGroup(addPrefix = true)
                }
            }
        }
    }
}

private fun Project.changeGroup(addPrefix: Boolean) {
    var group = group.toString()
    group = if (addPrefix) {
        "$GRADLE_PLUGIN_PREFIX$group"
    } else {
        group.removePrefix(GRADLE_PLUGIN_PREFIX)
    }

    setGradleProperty(GradleProperty.GROUP, group)
}

private fun Project.changeVersion(newVersion: Version) {
    setGradleProperty(GradleProperty.VERSION, newVersion.toString())
    version = newVersion.toString()
}

