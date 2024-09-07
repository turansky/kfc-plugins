package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.kotlin.dsl.listProperty
import javax.inject.Inject

abstract class BundlerEnvironmentExtension
@Inject
constructor(
    objects: ObjectFactory,
) {
    private val _variables: MutableList<EnvVariable> = mutableListOf()

    internal val variables: ListProperty<EnvVariable> =
        objects.listProperty<EnvVariable>()
            .convention(_variables)

    fun set(
        name: String,
        value: String,
    ) {
        _variables.add(EnvVariable(name, value))
    }
}
