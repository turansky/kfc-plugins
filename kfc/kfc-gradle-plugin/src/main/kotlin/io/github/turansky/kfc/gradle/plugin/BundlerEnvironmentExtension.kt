package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.listProperty
import javax.inject.Inject

abstract class BundlerEnvironmentExtension
@Inject
constructor(
    objects: ObjectFactory,
) {
    private val _variables: MutableList<EnvVariable> = mutableListOf()

    internal val variables: Provider<List<EnvVariable>> =
        objects.listProperty<EnvVariable>()
            .convention(_variables)

    fun set(
        name: String,
        value: String,
    ) {
        _variables.add(EnvVariable(name, value))
    }
}
