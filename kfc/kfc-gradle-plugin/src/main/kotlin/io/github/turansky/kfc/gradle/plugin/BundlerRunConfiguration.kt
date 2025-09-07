package io.github.turansky.kfc.gradle.plugin

import org.gradle.process.ExecOperations
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject

internal data class BundlerRunConfiguration(
    val npmProject: NpmProject,
    val execOperations: ExecOperations,
    val args: List<String>,
)
