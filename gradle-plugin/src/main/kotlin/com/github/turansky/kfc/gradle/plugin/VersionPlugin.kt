package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

class VersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks {
                register("setReleaseVersion") {
                    doLast {
                        setReleaseVersion()
                    }
                }

                register("setNextSnapshotVersion") {
                    doLast {
                        setNextSnapshotVersion()
                    }
                }
            }
        }
    }
}
