package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.GradleProperty.GROUP
import com.github.turansky.kfc.gradle.plugin.GradleProperty.VERSION
import com.github.turansky.kfc.gradle.plugin.JvmTarget.JVM_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import java.io.File

private const val GRADLE_PLUGIN_PREFIX = "gradle.plugin."
private val USE_GRADLE_PLUGIN_PREFIX = BooleanProperty("kfc.gradle.plugin.prefix")

class PluginPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.plugins.apply(RootPluginPublishPlugin::class)

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
        }
    }
}

private class RootPluginPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val useGradlePluginPrefix = property(USE_GRADLE_PLUGIN_PREFIX)

        tasks {
            register("preparePublish") {
                doLast {
                    changeVersion(Version::toRelease, project.versionFiles())
                    if (useGradlePluginPrefix) {
                        changeGroup(addPrefix = false)
                    }
                }
            }

            register("prepareDevelopment") {
                doLast {
                    changeVersion(Version::toNextSnapshot, project.versionFiles())
                    if (useGradlePluginPrefix) {
                        changeGroup(addPrefix = true)
                    }
                }
            }
        }
    }
}

private fun Project.versionFiles(): Set<File> =
    fileTree(projectDir)
        .matching {
            include("src/main/kotlin/**/KotlinPluginArtifact.kt")
            include("**/src/main/kotlin/**/KotlinPluginArtifact.kt")
        }
        .files

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

