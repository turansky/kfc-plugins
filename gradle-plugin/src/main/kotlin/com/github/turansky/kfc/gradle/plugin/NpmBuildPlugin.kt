package com.github.turansky.kfc.gradle.plugin

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
                group = DEFAULT_TASK_GROUP

                delete = setOf(npmDir)
            }

            val prepareNpmSources = register<Copy>("prepareNpmSources") {
                group = DEFAULT_TASK_GROUP

                from(project.buildDir.resolve("distributions"))
                into(npmDir)

                val outputFileName = jsOutputFileName
                include(outputFileName)
                rename(outputFileName, "index.js")
            }

            val prepareNpmPackage = register<Copy>("prepareNpmPackage") {
                group = DEFAULT_TASK_GROUP

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
