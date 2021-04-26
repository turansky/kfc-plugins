package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.GradleProperty.GROUP
import com.github.turansky.kfc.gradle.plugin.GradleProperty.VERSION
import com.github.turansky.kfc.gradle.plugin.JvmTarget.JVM_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

private const val KOTLIN_PLUGIN_ARTIFACT = "KotlinPluginArtifact.kt"
private const val GRADLE_PLUGIN_PREFIX = "gradle.plugin."

open class PluginPublishExtension {
    var gradlePluginPrefix: Boolean = false
}

class PluginPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val extension = extensions.create<PluginPublishExtension>("pluginPublish")

        tasks {
            configureEach<KotlinJvmCompile> {
                kotlinOptions {
                    jvmTarget = JVM_1_8
                    allWarningsAsErrors = true
                }
            }

            named<Jar>("jar") {
                into("META-INF") {
                    from("$projectDir/LICENSE.md")
                }
            }

            register("preparePublish") {
                doLast {
                    changeVersion(Version::toRelease, project.versionFiles())
                    if (extension.gradlePluginPrefix) {
                        changeGroup(addPrefix = false)
                    }
                }
            }

            register("prepareDevelopment") {
                doLast {
                    changeVersion(Version::toNextSnapshot, project.versionFiles())
                    if (extension.gradlePluginPrefix) {
                        changeGroup(addPrefix = true)
                    }
                }
            }
        }
    }
}

private fun Project.versionFiles(): Set<File> {
    val compileKotlin = tasks.findByName("compileKotlin") as KotlinCompile?

    return if (compileKotlin != null) {
        compileKotlin.source.matching { include("**/$KOTLIN_PLUGIN_ARTIFACT") }
    } else {
        fileTree(projectDir).matching { include("**/src/main/kotlin/**/$KOTLIN_PLUGIN_ARTIFACT") }
    }.files
}

private fun Project.changeGroup(addPrefix: Boolean) {
    var group = group.toString()
    group = if (addPrefix) {
        "$GRADLE_PLUGIN_PREFIX$group"
    } else {
        group.removePrefix(GRADLE_PLUGIN_PREFIX)
    }

    setGradleProperty(GROUP, group)
}

private fun Project.changeVersion(
    change: (Version) -> Version,
    versionFiles: Set<File>
) {
    val oldVersion = currentVersion.toString()
    val newVersion = change(currentVersion).toString()

    setGradleProperty(VERSION, newVersion)
    version = newVersion

    for (file in versionFiles) {
        val content = file.readText()
        val newContent = content.replace(oldVersion, newVersion)

        check(content != newContent) {
            "Unable to found plugin version in file: $file"
        }

        file.writeText(newContent)
    }
}

