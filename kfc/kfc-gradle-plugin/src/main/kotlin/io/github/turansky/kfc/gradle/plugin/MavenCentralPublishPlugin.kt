package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin

class MavenCentralPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            configurePublication()
        }

        plugins.withId(KotlinPlugin.JVM) {
            configurePublication()
        }
    }

    private fun Project.configurePublication() {
        plugins.apply(MavenPublishPlugin::class)
        plugins.apply(SigningPlugin::class)

        // TODO: move to common
        val releaseMode = hasProperty("signing.keyId")
        val multiplatformMode = plugins.hasPlugin(KotlinPlugin.MULTIPLATFORM)

        val javadocJar = tasks.register("emptyJavadocJar", Jar::class) {
            group = DEFAULT_TASK_GROUP

            archiveClassifier.set("javadoc")
        }

        configure<PublishingExtension> {
            if (multiplatformMode) {
                publications.withType<MavenPublication>().configureEach {
                    if (name == "jvm")
                        artifact(javadocJar.get())

                    pom.configure(project, releaseMode)
                }
            } else {
                publications.create<MavenPublication>("mavenKotlin") {
                    from(components["kotlin"])

                    artifact(tasks.getByName(KOTLIN_SOURCES_TASK))
                    artifact(javadocJar.get())

                    pom.configure(project, releaseMode)
                }
            }
        }

        if (releaseMode) {
            val publishing = extensions.getByName<PublishingExtension>("publishing")

            configure<SigningExtension> {
                sign(publishing.publications)
            }
        }
    }
}
