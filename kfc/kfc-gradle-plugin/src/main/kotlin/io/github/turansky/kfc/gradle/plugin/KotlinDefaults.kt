package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

private const val DOM_API_INCLUDED = "kotlin.js.stdlib.dom.api.included"
private const val OUTPUT_GRANULARITY = "kotlin.js.ir.output.granularity"

private val PER_FILE = BooleanProperty("kfc.per.file", true)
private val STRICT_MODE = BooleanProperty("kfc.strict.mode", true)

internal fun Project.applyKotlinDefaults() {
    ext(DOM_API_INCLUDED, false)

    val granularity = if (property(PER_FILE)) "per-file" else "whole-program"
    ext(OUTPUT_GRANULARITY, granularity)

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
