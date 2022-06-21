package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin as StandardMavenPublishPlugin

class MavenCentralPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.withId(KotlinPlugin.MULTIPLATFORM) {
            configurePublication()
        }

        plugins.withId(KotlinPlugin.JVM) {
            configurePublication()
        }

        plugins.withId(KotlinPlugin.JS) {
            configurePublication()
        }
    }

    private fun Project.configurePublication() {
        plugins.apply(StandardMavenPublishPlugin::class)
        plugins.apply(SigningPlugin::class)

        val releaseMode = hasProperty("signing.keyId")

        fun pomProperty(name: String): String =
            property("kfc.pom.$name") as String

        val multiplatformMode = plugins.hasPlugin(KotlinPlugin.MULTIPLATFORM)
        val jvmMode = plugins.hasPlugin(KotlinPlugin.JVM)

        val javadocJar = if (multiplatformMode || jvmMode) {
            tasks.register("emptyJavadocJar", Jar::class) {
                archiveClassifier.set("javadoc")
            }
        } else null

        if (multiplatformMode) {
            configure<PublishingExtension> {
                publications {
                    withType<MavenPublication>().configureEach {
                        if (name == "jvm")
                            artifact(javadocJar!!.get())

                        pom.configure(::pomProperty, releaseMode)
                    }
                }
            }
        } else {
            configure<PublishingExtension> {
                publications {
                    create<MavenPublication>("mavenKotlin") {
                        from(components["kotlin"])

                        artifact(tasks.named("kotlinSourcesJar").get())

                        if (javadocJar != null)
                            artifact(javadocJar.get())

                        pom.configure(::pomProperty, releaseMode)
                    }
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
