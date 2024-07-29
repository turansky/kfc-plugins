package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import java.io.File

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
        val relatedResources by registering(RelatedResources::class)
        val resources by lazy { relatedResources.get().calculate() }

        val patchWebpackConfig by registering(PatchWebpackConfig::class) {
            doFirst {
                addResourceModules(resources)
            }

            dependsOn(relatedResources)

            patch(
                "default-settings",
                """
                if (!!config.output) { 
                  config.output.chunkFilename = '${project.jsChunkFileName}'
                  config.output.clean = true
                }
                """.trimIndent()
            )

            doFirst {
                val momentjsInstalled = project.rootProject
                    .layout.buildDirectory.asFile.get()
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

        configureEach<KotlinWebpack> {
            dependsOn(patchWebpackConfig)
        }

        configureEach<KotlinJsTest> {
            dependsOn(patchWebpackConfig)
        }
    }
}

private fun PatchWebpackConfig.addResourceModules(
    resources: List<File>,
) {
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
