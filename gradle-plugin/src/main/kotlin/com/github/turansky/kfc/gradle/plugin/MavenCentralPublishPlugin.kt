package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin as StandardMavenPublishPlugin

class MavenCentralPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        plugins.apply(StandardMavenPublishPlugin::class)
        plugins.apply(SigningPlugin::class)

        fun pomProperty(name: String): String =
            property("kfc.pom.$name") as String

        configure<PublishingExtension> {
            publications {
                create<MavenPublication>("mavenKotlin") {
                    from(components["kotlin"])
                    artifact(tasks.named("kotlinSourcesJar").get())

                    pom.configure(::pomProperty)
                }
            }
        }

        if (hasProperty("signing.keyId")) {
            val publishing = extensions.getByName<PublishingExtension>("publishing")

            configure<SigningExtension> {
                sign(publishing.publications)
            }
        }
    }
}

private fun MavenPom.configure(
    pomProperty: (name: String) -> String
) {
    val projectUrl = pomProperty("url")
    val connectionUrl = "scm:git:$projectUrl.git"

    name.set(pomProperty("name"))
    description.set(pomProperty("description"))
    url.set(projectUrl)
    inceptionYear.set(pomProperty("inception.year"))

    licenses {
        license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }

    developers {
        developer {
            id.set(pomProperty("developer.id"))
            name.set(pomProperty("developer.name"))
            email.set(pomProperty("developer.email"))
        }
    }

    scm {
        connection.set(connectionUrl)
        developerConnection.set(connectionUrl)
        url.set(projectUrl)
    }
}
