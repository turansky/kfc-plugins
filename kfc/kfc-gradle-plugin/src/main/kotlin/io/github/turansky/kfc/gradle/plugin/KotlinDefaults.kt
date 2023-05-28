package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

private const val NO_WARN: String = "kotlin.mpp.stability.nowarn"

private const val JS_COMPILER = "kotlin.js.compiler"
private const val OUTPUT_GRANULARITY = "kotlin.js.ir.output.granularity"

private val STRICT_MODE = BooleanProperty("kfc.strict.mode", true)

internal fun Project.applyKotlinDefaults() {
    rootProject.ext(NO_WARN, true)

    ext(JS_COMPILER, "ir")
    ext(OUTPUT_GRANULARITY, "whole-program")

    plugins.apply(SourceMapsPlugin::class)

    configureStrictMode()
    disableTestsWithoutSources()

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

private fun Project.disableTestsWithoutSources() {
    afterEvaluate {
        val sourceSet = SourceSet.values()
            .single { it.taskNames.mapNotNull(tasks::findByPath).isNotEmpty() }

        sourceSet.taskNames
            .mapNotNull(tasks::findByPath)
            .forEach { task ->
                task.onlyIf {
                    val kotlin = project.extensions.getByName<KotlinProjectExtension>("kotlin")
                    sourceSet.names
                        .asSequence()
                        .map { kotlin.sourceSets.getByName(it) }
                        .flatMap { it.kotlin.sourceDirectories }
                        .any { it.exists() }
                }
            }
    }
}

private enum class SourceSet(
    val names: Set<String>,
    val taskPrefixes: Set<String>,
) {
    MULTIPLATFORM(
        names = setOf("jsTest", "commonTest"),
        taskPrefixes = setOf("jsTest", "jsIrTest", "jsLegacyTest")
    ),

    JS(
        names = setOf("test"),
        taskPrefixes = setOf("test", "irTest", "legacyTest")
    ),

    ;

    val taskNames = taskPrefixes.map { "${it}PackageJson" }
}
