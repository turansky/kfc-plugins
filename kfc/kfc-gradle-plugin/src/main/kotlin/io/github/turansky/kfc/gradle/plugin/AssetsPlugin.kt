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
            val generateAssets by tasks.registering(GenerateAssets::class) {
                pkg = assetsPackage
                factoryName = propertyOrNull(ASSETS_FACTORY)
                templateColor = propertyOrNull(ASSETS_TEMPLATE_COLOR)
                resourcesDirectory = sequenceOf(
                    file("src/clientCommonMain/resources/assets"),
                    file("src/jsMain/resources/assets"),
                ).first { it.exists() }
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets["jsMain"].kotlin.srcDir(generateAssets)
            }
        }
    }
}
