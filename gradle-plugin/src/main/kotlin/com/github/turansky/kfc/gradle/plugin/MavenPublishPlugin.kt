package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin as StandardMavenPublishPlugin

private val REPO_URL = StringProperty("kfc.publish.maven.repo.url")
private val SNAPSHOT_REPO_URL = StringProperty("kfc.publish.maven.snapshot.repo.url")

class MavenPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            configurePublication()
        }

        plugins.withId(KotlinPlugin.JS) {
            configurePublication(JS_SOURCES_JAR_TASK)
        }
    }
}

private fun Project.configurePublication(sourceTaskName: String? = null) {
    plugins.apply(StandardMavenPublishPlugin::class)

    plugins.withType<StandardMavenPublishPlugin> {
        configure<PublishingExtension> {
            publications {
                create<MavenPublication>("mavenKotlin") {
                    from(components["kotlin"])
                    if (sourceTaskName != null) {
                        artifact(tasks.named<Jar>(sourceTaskName).get())
                    }
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

private fun Project.mavenRepoUrl(): String? =
    if (!currentVersion.snapshot) {
        propertyOrNull(REPO_URL)
    } else {
        propertyOrNull(SNAPSHOT_REPO_URL)
            ?: propertyOrNull(REPO_URL)
    }
