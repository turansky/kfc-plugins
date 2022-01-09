package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import java.io.File

private class ReactDatesRootPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.named("kotlinNpmInstall") {
            doLast {
                project.buildDir
                    .resolve("js/node_modules/react-dates/lib")
                    .let(::fileTree)
                    .matching { include("**/*.js") }
                    .forEach(::applyPatch)
            }
        }
    }

    private fun applyPatch(file: File) {
        val content = file.readText()
        if ("UNSAFE_" in content)
            return

        val newContent = content
            .replace("componentWillReceiveProps", "UNSAFE_componentWillReceiveProps")
            .replace("componentWillUpdate", "UNSAFE_componentWillUpdate")

        if (content != newContent) {
            file.writeText(newContent)
        }
    }
}

class ReactDatesPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.plugins.apply(ReactDatesRootPlugin::class)
    }
}
