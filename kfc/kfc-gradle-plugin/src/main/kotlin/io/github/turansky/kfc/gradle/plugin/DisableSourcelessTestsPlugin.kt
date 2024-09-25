package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class DisableSourcelessTestsPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        afterEvaluate {
            tasks.named("jsTestPackageJson") {
                onlyIf {
                    val kotlin = project.extensions.getByName<KotlinProjectExtension>("kotlin")
                    sequenceOf("jsTest", "commonTest")
                        .map { kotlin.sourceSets.getByName(it) }
                        .flatMap { it.kotlin.sourceDirectories }
                        .any { it.exists() }
                }
            }
        }
    }
}
