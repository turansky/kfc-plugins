package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.named
import java.io.File

private const val KT_38040 = "KT-38040"
private val KT_38040_FLAG = BooleanProperty("kfc.workaround.$KT_38040")

// language=JavaScript
private const val KT_38040_PATCH = """
// https://youtrack.jetbrains.com/issue/KT-38040
;(function () {
    config.browsers = ["ChromeHeadlessNoSandbox"]
    config.customLaunchers = {
        ChromeHeadlessNoSandbox: {
            base: "ChromeHeadless",
            flags: ["--no-sandbox"],
        },
    }
})()
"""

internal class WorkaroundPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        if (property(KT_38040_FLAG)) {
            afterEvaluate {
                tasks.named<Delete>("clean") {
                    delete(karmaConfigDir())
                }

                sequenceOf(
                    tasks.findByName("browserTest"),
                    tasks.findByName("jsBrowserTest")
                ).filterNotNull()
                    .forEach {
                        it.applyKarmaPatch(KT_38040, KT_38040_PATCH)
                    }
            }
        }
    }
}

private fun Task.karmaConfigDir(): File =
    project.projectDir.resolve("karma.config.d")

private fun Task.applyKarmaPatch(name: String, body: String) {
    val configDir = karmaConfigDir()
    configDir.mkdir()

    configDir.resolve("$name.js")
        .writeText(body)
}
