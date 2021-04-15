package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            tasks {
                applyConfiguration()
            }
        }

        plugins.withId(KotlinPlugin.JS) {
            tasks {
                applyConfiguration()
            }
        }
    }

    private fun TaskContainerScope.applyConfiguration() {
        val patchWebpackConfig = register<PatchWebpackConfig>("patchWebpackConfig") {
            addResourceModules()

            if (project.property(Momentjs.IGNORE_LOCALES_FLAG))
                patch("momentjs-locales-ignore", Momentjs.IGNORE_LOCALES_PATCH)
        }

        named<Delete>("clean") {
            delete(patchWebpackConfig)
        }

        configureEach<KotlinJsCompile> {
            dependsOn(patchWebpackConfig)
        }
    }
}

private fun PatchWebpackConfig.addResourceModules() {
    val resources = project.relatedResources()
    if (resources.isEmpty()) {
        return
    }

    val paths = resources.joinToString(",\n") {
        it.toPathString()
    }

    // language=JavaScript
    val body = """
        config.resolve.modules.unshift(
            $paths
        )
    """.trimIndent()

    patch("resources", body)
}
