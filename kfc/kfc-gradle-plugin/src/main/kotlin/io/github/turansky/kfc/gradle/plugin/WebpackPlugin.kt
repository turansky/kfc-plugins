package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

class WebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(LatestNodePlugin::class)
        plugins.apply(LatestWebpackPlugin::class)

        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            tasks {
                applyConfiguration()
            }
        }
    }

    private fun TaskContainerScope.applyConfiguration() {
        val patchWebpackConfig = register<PatchWebpackConfig>("patchWebpackConfig") {
            addResourceModules()

            patch(
                "chunk-name",
                """
                    if (!!config.output) 
                      config.output.chunkFilename = '${project.jsChunkFileName}'
                """.trimIndent()
            )

            doFirst {
                val momentjsInstalled = project.rootProject.buildDir
                    .resolve("js/node_modules/moment")
                    .exists()

                if (momentjsInstalled && !patches.containsKey(Momentjs.PATCH_NAME)) {
                    patches[Momentjs.PATCH_NAME] = Momentjs.IGNORE_LOCALES_PATCH
                }
            }

            dependsOn(":kotlinNpmInstall")
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
