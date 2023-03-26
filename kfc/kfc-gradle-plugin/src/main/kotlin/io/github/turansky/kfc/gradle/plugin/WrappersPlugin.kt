package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class WrappersPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            dependencies {
                applyConfiguration(
                    configurationName = JS_MAIN_IMPLEMENTATION,
                    wrappersVersion = wrappersVersion(),
                )
            }
        }

        extensions.create(
            WrappersDependencyExtension::class.java,
            "wrappers",
            WrappersDependencyExtensionImpl::class.java,
        )
    }

    private fun DependencyHandlerScope.applyConfiguration(
        configurationName: String,
        wrappersVersion: String,
    ) {
        configurationName(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$wrappersVersion"))
    }
}

private fun Project.wrappersVersion(): String =
    version("kotlin-wrappers")

private fun Project.version(
    target: String,
): String =
    property("${target}.version") as String
