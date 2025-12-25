package io.github.turansky.kfc.gradle.plugin

import org.gradle.internal.service.ServiceRegistry
import org.gradle.process.ExecOperations

internal data class BundlerRunConfiguration(
    private val bundler: Bundler,
    val options: ExecOptions,
    val execOperations: ExecOperations,
    val services: ServiceRegistry,
    val continuous: Boolean = false,
) {
    val id: String
        get() = bundler.toolName
}
