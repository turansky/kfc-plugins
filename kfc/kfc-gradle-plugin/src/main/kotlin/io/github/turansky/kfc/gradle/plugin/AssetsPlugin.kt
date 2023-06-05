package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private val ASSETS_PACKAGE = StringProperty("kfc.assets.package")
private val ASSETS_FACTORY = StringProperty("kfc.assets.factory")
private val ASSETS_TEMPLATE_COLOR = StringProperty("kfc.assets.template.color")

class AssetsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val assetsPackage = propertyOrNull(ASSETS_PACKAGE)
            ?: return@with

        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            val clientCommonAssets = file("src/clientCommonMain/resources/assets")
            val jsAssets = file("src/jsMain/resources/assets")

            val multiplatform = clientCommonAssets.exists()

            val generateAssets by tasks.registering(GenerateAssets::class) {
                multiplatformMode = multiplatform

                pkg = assetsPackage
                factoryName = propertyOrNull(ASSETS_FACTORY)
                templateColor = propertyOrNull(ASSETS_TEMPLATE_COLOR)
                resourcesDirectory = sequenceOf(clientCommonAssets, jsAssets).first { it.exists() }
            }

            val generateClientCommonAssets by tasks.registering {
                outputs.dir(generateAssets.get().clientCommonOutputDirectory)
                dependsOn(generateAssets)
            }

            val generateMobileCommonAssets by tasks.registering {
                outputs.dir(generateAssets.get().mobileCommonOutputDirectory)
                dependsOn(generateAssets)
            }

            val generateJsAssets by tasks.registering {
                outputs.dir(generateAssets.get().jsOutputDirectory)
                dependsOn(generateAssets)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                if (multiplatform) {
                    afterEvaluate {
                        sourceSets["clientCommonMain"].kotlin.srcDir(generateClientCommonAssets)
                        sourceSets["mobileCommonMain"].kotlin.srcDir(generateMobileCommonAssets)
                    }
                }

                sourceSets["jsMain"].kotlin.srcDir(generateJsAssets)
            }
        }
    }
}
