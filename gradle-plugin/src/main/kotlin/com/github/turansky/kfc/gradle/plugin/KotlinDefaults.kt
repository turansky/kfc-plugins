package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

private const val BUILD_DISTRIBUTION = "kotlin.js.generate.executable.default"

private val STRICT_MODE = BooleanProperty("kfc.strict.mode", true)

internal fun Project.applyKotlinDefaults() {
    ext(BUILD_DISTRIBUTION, false)

    configureStrictMode()
    disableTestsWithoutSources()
}

private fun Project.configureStrictMode() {
    if (property(STRICT_MODE)) {
        tasks.withType<KotlinCompile<*>>().configureEach {
            kotlinOptions.allWarningsAsErrors = true
        }
    }
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
