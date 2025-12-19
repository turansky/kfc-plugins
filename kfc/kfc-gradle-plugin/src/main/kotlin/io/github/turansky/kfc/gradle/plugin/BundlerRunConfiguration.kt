package io.github.turansky.kfc.gradle.plugin

import org.gradle.internal.service.ServiceRegistry
import org.gradle.process.ExecOperations
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject

internal data class BundlerRunConfiguration(
    val npmProject: NpmProject,
    val nodeExecutable: String,
    val execOperations: ExecOperations,
    val services: ServiceRegistry,
    val bundler: Bundler,
    val args: List<String>,
    val continuous: Boolean = false,
)
