package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.kotlin.dsl.assign

internal fun MavenPom.configure(
    project: Project,
    releaseMode: Boolean,
) {
    fun pomProperty(name: String): String =
        project.property("kfc.pom.$name") as String

    val projectUrl = pomProperty("url")
    val connectionUrl = "scm:git:$projectUrl.git"

    name = pomProperty("name")
    description = pomProperty("description")
    url = projectUrl
    inceptionYear = pomProperty("inception.year")

    licenses {
        license {
            name = "The Apache License, Version 2.0"
            url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
        }
    }

    if (releaseMode) {
        developers {
            developer {
                id = pomProperty("developer.id")
                name = pomProperty("developer.name")
                email = pomProperty("developer.email")
            }
        }
    }

    scm {
        connection = connectionUrl
        developerConnection = connectionUrl
        url = projectUrl
    }
}
