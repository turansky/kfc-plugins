package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.model.ObjectFactory
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
    objects: ObjectFactory,
) : BundlerEnvironmentExtension {
    private val _variables: MutableList<EnvVariable> = mutableListOf()

    val variables: Provider<List<EnvVariable>> =
        objects.listProperty<EnvVariable>()
            .convention(_variables)

    override fun set(
        name: String,
        value: String,
    ) {
        _variables.add(EnvVariable(name, value))
    }
}
