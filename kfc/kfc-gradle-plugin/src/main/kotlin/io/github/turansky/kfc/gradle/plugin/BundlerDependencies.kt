package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension

internal fun Project.setBundlerDevDependencies(
    vararg deps: BundlerDependency,
) {
    afterEvaluate {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            dependencies {
                applyConfiguration(
                    configurationName = JS_MAIN_IMPLEMENTATION,
                    deps = deps,
                )
            }
        }
    }
}

internal data class BundlerDependency(
    val name: String,
    val version: String,
)

private fun DependencyHandlerScope.applyConfiguration(
    configurationName: String,
    vararg deps: BundlerDependency,
) {
    val devNpm = extensions.getByName<DevNpmDependencyExtension>("devNpm")

    deps.forEach {
        configurationName(devNpm(it.name, it.version))
    }
}
