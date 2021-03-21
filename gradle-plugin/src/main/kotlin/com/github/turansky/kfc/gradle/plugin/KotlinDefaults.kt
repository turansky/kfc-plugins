package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

private const val BUILD_DISTRIBUTION = "kotlin.js.generate.executable.default"

internal fun Project.applyKotlinDefaults() {
    ext(BUILD_DISTRIBUTION, false)

    disableTestsWithoutSources()
}

private fun Project.disableTestsWithoutSources() {
    afterEvaluate {
        val sourceSetName = sequenceOf("jsTest", "test")
            .single { tasks.findByPath("${it}PackageJson") != null }

        tasks.named("${sourceSetName}PackageJson") {
            onlyIf {
                val kotlin = project.extensions.getByName<KotlinProjectExtension>("kotlin")
                val sourceDir = kotlin.sourceSets
                    .getByName(sourceSetName)
                    .kotlin.sourceDirectories
                    .singleOrNull()

                sourceDir?.exists() ?: true
            }
        }
    }
}
