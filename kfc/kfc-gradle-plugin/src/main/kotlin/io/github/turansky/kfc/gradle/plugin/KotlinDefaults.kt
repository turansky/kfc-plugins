package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

private const val DOM_API_INCLUDED = "kotlin.js.stdlib.dom.api.included"
private const val OUTPUT_GRANULARITY = "kotlin.js.ir.output.granularity"

private val STRICT_MODE = BooleanProperty("kfc.strict.mode", true)

internal fun Project.applyKotlinDefaults() {
    ext(DOM_API_INCLUDED, false)
    ext(OUTPUT_GRANULARITY, "whole-program")

    plugins.apply(SourceMapsPlugin::class)

    configureStrictMode()
    disableTestsWithoutSources()

    extensions.create<NpmvDependencyExtension>("npmv")
}

private fun Project.configureStrictMode() {
    if (property(STRICT_MODE)) {
        tasks.configureEach<KotlinCompilationTask<*>> {
            compilerOptions {
                allWarningsAsErrors.set(true)
            }
        }
    }
}

private fun Project.disableTestsWithoutSources() {
    tasks.findByPath("jsTestPackageJson")?.configure<Task> {
        onlyIf {
            val kotlin = project.extensions.getByName<KotlinProjectExtension>("kotlin")
            sequenceOf("jsTest", "commonTest")
                .map { kotlin.sourceSets.getByName(it) }
                .flatMap { it.kotlin.sourceDirectories }
                .any { it.exists() }
        }
    }
}
