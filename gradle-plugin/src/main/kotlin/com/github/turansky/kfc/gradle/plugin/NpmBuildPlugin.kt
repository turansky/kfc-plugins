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
        val npmDir = rootDir.resolve("npm")
        val npmCommonTemplateDir = npmDir.resolve("common/template")
        val npmTemplateDir = npmDir.resolve("$name/template")
        val npmBuildDir = npmDir.resolve("$name/build")
        val npmSourceDir = npmBuildDir.resolve("src")

        tasks {
            val cleanNpmBuild = register<Delete>("cleanNpmBuild") {
                delete = setOf(npmBuildDir)
            }

            val prepareNpmSources = register<Copy>("prepareNpmSources") {
                from(buildDir.resolve("distributions"))
                into(npmSourceDir)
                include(COMPONENT.fileName)
            }

            val prepareNpmPackage = register<Copy>("prepareNpmPackage") {
                from(npmCommonTemplateDir)
                from(npmTemplateDir)
                into(npmBuildDir)
            }

            register("prepareNpmBuild") {
                dependsOn(cleanNpmBuild)
                dependsOn(prepareNpmSources)
                dependsOn(prepareNpmPackage)
            }
        }
    }
}
