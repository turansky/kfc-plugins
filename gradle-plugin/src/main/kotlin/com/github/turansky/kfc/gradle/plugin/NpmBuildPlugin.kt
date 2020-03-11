package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.Output.COMPONENT
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register

class NpmBuildPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val npmDir = buildDir.resolve("npm")

        tasks {
            val cleanNpmBuild = register<Delete>("cleanNpmBuild") {
                delete = setOf(npmDir)
            }

            val prepareNpmSources = register<Copy>("prepareNpmSources") {
                from(project.buildDir.resolve("distributions"))
                into(npmDir)

                include(COMPONENT.fileName)
                rename(COMPONENT.fileName, "index.js")
            }

            val prepareNpmPackage = register<Copy>("prepareNpmPackage") {
                from(project.projectDir)
                into(npmDir)

                include("package.json")
                include("index.d.ts")
                include(".npmignore")
            }

            register("prepareNpmBuild") {
                dependsOn(cleanNpmBuild)
                dependsOn(prepareNpmSources)
                dependsOn(prepareNpmPackage)
            }
        }
    }
}
