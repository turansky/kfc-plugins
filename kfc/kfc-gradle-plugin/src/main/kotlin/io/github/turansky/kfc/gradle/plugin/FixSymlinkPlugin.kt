package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.targets.js.npm.PackageJson
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinPackageJsonTask

private const val JS_EXTENSION = ".js"
private const val MJS_EXTENSION = ".mjs"

// TODO: Remove after migration to Kotlin 2.0
//  WA: For ESM bundles there's incorrect package.json "main" field defined
class FixSymlinkPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks.configureEach<KotlinPackageJsonTask> {
            packageJsonHandlers.add {
                fixMainField()
            }
        }
    }
}

private fun PackageJson.fixMainField() {
    val mainField = main ?: return

    if (mainField.endsWith(JS_EXTENSION)) {
        main = mainField.removeSuffix(JS_EXTENSION) + MJS_EXTENSION
    }
}
