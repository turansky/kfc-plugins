package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

private const val DOM_API_INCLUDED = "kotlin.js.stdlib.dom.api.included"
private const val OUTPUT_GRANULARITY = "kotlin.js.ir.output.granularity"

private val STRICT_MODE = BooleanProperty("kfc.strict.mode", true)
private val LEGACY = BooleanProperty("kfc.legacy")

internal fun Project.applyKotlinDefaults() {
    ext(DOM_API_INCLUDED, false)
    ext(OUTPUT_GRANULARITY, if (property(LEGACY)) "whole-program" else "per-file")

    plugins.apply(SourceMapsPlugin::class)
    plugins.apply(DisableSourcelessTestsPlugin::class)

    configureStrictMode()

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
