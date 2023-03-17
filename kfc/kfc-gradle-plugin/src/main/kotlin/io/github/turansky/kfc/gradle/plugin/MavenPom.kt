package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom

internal fun MavenPom.configure(
    project: Project,
    releaseMode: Boolean,
) {
    fun pomProperty(name: String): String =
        project.property("kfc.pom.$name") as String

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

    if (releaseMode) {
        developers {
            developer {
                id.set(pomProperty("developer.id"))
                name.set(pomProperty("developer.name"))
                email.set(pomProperty("developer.email"))
            }
        }
    }

    scm {
        connection.set(connectionUrl)
        developerConnection.set(connectionUrl)
        url.set(projectUrl)
    }
}
