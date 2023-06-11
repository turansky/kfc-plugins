package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

class VersionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks {
            register("setReleaseVersion") {
                group = DEFAULT_TASK_GROUP

                doLast {
                    setReleaseVersion()
                }
            }

            register("setNextSnapshotVersion") {
                group = DEFAULT_TASK_GROUP

                doLast {
                    setNextSnapshotVersion()
                }
            }
        }
    }
}
