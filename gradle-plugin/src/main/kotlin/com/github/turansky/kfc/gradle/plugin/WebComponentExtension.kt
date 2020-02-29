package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.WebComponent.Method
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property.Type
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property.Type.*

open class WebComponentExtension {
    var id: String? = null
    var source: String? = null

    private val _properties: MutableList<Property> = mutableListOf()
    private val _methods: MutableList<Method> = mutableListOf()
    private val _events: MutableList<String> = mutableListOf()

    fun property(name: String) {
        addProperty(name, RW)
    }

    fun getter(name: String) {
        addProperty(name, RO)
    }

    fun setter(name: String) {
        addProperty(name, WO)
    }

    fun method(name: String, vararg parameterNames: String) {
        _methods += Method(name, parameterNames.toList())
    }

    fun event(type: String) {
        _events += type
    }

    private fun addProperty(name: String, type: Type) {
        _properties += Property(name, type)
    }

    internal fun build(): WebComponent =
        WebComponent(
            id = requireNotNull(id),
            properties = _properties.toList(),
            methods = _methods.toList(),
            events = _events.toList(),
            source = requireNotNull(source)
        )
}
