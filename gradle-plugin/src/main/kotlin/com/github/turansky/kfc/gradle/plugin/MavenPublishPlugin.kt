package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin as StandardMavenPublishPlugin

private val JS_SOURCES_JAR_TASK = "JsSourcesJar"

private val REPO_URL = "kfc.publish.maven.repo.url"
private val SNAPSHOT_REPO_URL = "kfc.publish.maven.snapshot.repo.url"

class MavenPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withType<KotlinJsPluginWrapper> {
            plugins.apply(StandardMavenPublishPlugin::class)

            plugins.withType<StandardMavenPublishPlugin> {
                configure<PublishingExtension> {
                    publications {
                        register("mavenKotlin", MavenPublication::class) {
                            from(components["kotlin"])
                            artifact(tasks.named<Jar>(JS_SOURCES_JAR_TASK).get())
                        }
                    }

                    mavenRepoUrl()?.let { repoUrl ->
                        repositories {
                            maven { url = uri(repoUrl) }
                        }
                    }
                }
            }
        }
    }
}

private fun Project.mavenRepoUrl(): String? =
    if (!currentVersion.snapshot) {
        propertyOrNull(REPO_URL)
    } else {
        propertyOrNull(SNAPSHOT_REPO_URL)
            ?: propertyOrNull(REPO_URL)
    }
