package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

private const val JS_COMPILER = "kotlin.js.compiler"
private const val BUILD_DISTRIBUTION = "kotlin.js.generate.executable.default"
private const val OUTPUT_GRANULARITY = "kotlin.js.ir.output.granularity"

private val STRICT_MODE = BooleanProperty("kfc.strict.mode", true)
private val PROGRESSIVE_MODE = BooleanProperty("kfc.progressive.mode", true)

internal val Project.jsIrCompiler: Boolean
    get() = findProperty(JS_COMPILER) == "ir"

internal fun Project.applyKotlinDefaults(
    both: Boolean,
    singleFile: Boolean = false,
) {
    if (both && property(PROGRESSIVE_MODE)) {
        ext(JS_COMPILER, "both")
    }
    ext(BUILD_DISTRIBUTION, false)

    if (singleFile) {
        ext(OUTPUT_GRANULARITY, "whole-program")
    }

    plugins.apply(SourceMapsPlugin::class)
    plugins.apply(WorkaroundPlugin::class)
    plugins.apply(ValueClassSerializationPlugin::class)

    configureStrictMode()

    extensions.create(
        NpmvDependencyExtension::class.java,
        "npmv",
        NpmvDependencyExtensionImpl::class.java,
        project
    )
}

private fun Project.configureStrictMode() {
    if (property(STRICT_MODE)) {
        tasks.configureEach<KotlinCompile<*>> {
            kotlinOptions.allWarningsAsErrors = true
        }
    }
}
