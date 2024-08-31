package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.listProperty

internal const val BUNDLER_ENVIRONMENT = "bundlerEnvironment"

interface BundlerEnvironmentExtension {
    fun set(
        name: String,
        value: String,
    )
}

internal open class BundlerEnvironmentExtensionImpl(
    project: Project,
) : BundlerEnvironmentExtension {
    private val _variables: MutableList<EnvVariable> = mutableListOf()

    val variables: Provider<List<EnvVariable>> =
        project.objects.listProperty<EnvVariable>()
            .convention(_variables)

    override fun set(
        name: String,
        value: String,
    ) {
        _variables.add(EnvVariable(name, value))
    }
}
