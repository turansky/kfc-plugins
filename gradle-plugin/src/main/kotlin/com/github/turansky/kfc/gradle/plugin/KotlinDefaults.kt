package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

private const val JS_COMPILER = "kotlin.js.compiler"
private const val BUILD_DISTRIBUTION = "kotlin.js.generate.executable.default"

private val STRICT_MODE = BooleanProperty("kfc.strict.mode", true)
private val PROGRESSIVE_MODE = BooleanProperty("kfc.progressive.mode", true)

internal val Project.jsIrCompiler: Boolean
    get() = findProperty(JS_COMPILER) == "ir"

internal fun Project.applyKotlinDefaults(both: Boolean) {
    if (both && property(PROGRESSIVE_MODE)) {
        ext(JS_COMPILER, "both")
    }
    ext(BUILD_DISTRIBUTION, false)

    plugins.apply(SourceMapsPlugin::class)
    plugins.apply(WorkaroundPlugin::class)

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
            .forEach {
                it.onlyIf {
                    val kotlin = project.extensions.getByName<KotlinProjectExtension>("kotlin")
                    val sourceDir = kotlin.sourceSets
                        .getByName(sourceSet.id)
                        .kotlin.sourceDirectories
                        .singleOrNull()

                    sourceDir?.exists() ?: true
                }
            }
    }
}

private enum class SourceSet(
    vararg names: String
) {
    MULTIPLATFORM("jsTest", "jsIrTest", "jsLegacyTest"),
    JS("test", "irTest", "legacyTest"),

    ;

    val id = names.first()
    val taskNames = names.map { "${it}PackageJson" }
}
