package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.targets.js.npm.*
import java.util.*
import javax.inject.Inject

private val EXTENSIONS = arrayOf(
    NpmvDependencyExtension::class, // npmv
    DevNpmvDependencyExtension::class, // devNpmv
    PeerNpmvDependencyExtension::class, // peerNpmv
)

internal fun Project.configureNpmExtensions() {
    for (pluginClass in EXTENSIONS) {
        val extensionName = (pluginClass.simpleName ?: continue)
            .removeSuffix("DependencyExtension")
            .replaceFirstChar { it.lowercase(Locale.getDefault()) }

        extensions.create(
            name = extensionName,
            type = pluginClass,
        )
    }
}

abstract class BaseNpmvDependencyExtension(
    private val npmExt: Provider<BaseNpmDependencyExtension>,
) {
    // A way to declare NPM dependency from a version catalog
    // Can be removed after an issue is fixed in KGP
    // Issue: https://youtrack.jetbrains.com/issue/KT-48519/KJS-Add-npm-dependency-method-for-version-catalog
    operator fun invoke(dependency: Provider<MinimalExternalModuleDependency>): Provider<NpmDependency> {
        val ext = npmExt.get()
        return dependency.map { ext(it.name, requireNotNull(it.version)) }
    }
}

abstract class NpmvDependencyExtension
@Inject constructor(val project: Project) :
    BaseNpmvDependencyExtension(
        project.provider { project.dependencies.the<NpmDependencyExtension>() }
    )

abstract class DevNpmvDependencyExtension
@Inject constructor(val project: Project) :
    BaseNpmvDependencyExtension(
        project.provider { project.dependencies.the<DevNpmDependencyExtension>() }
    )

abstract class PeerNpmvDependencyExtension
@Inject constructor(val project: Project) :
    BaseNpmvDependencyExtension(
        project.provider { project.dependencies.the<PeerNpmDependencyExtension>() }
    )
