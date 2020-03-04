package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.WebComponent.Method
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property.Type
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property.Type.*

open class WebComponentExtension {
    var id: String = "<id>"
    var shadowMode: String = "open"
    var source: String = "<source>"

    private val properties: MutableList<Property> = mutableListOf()
    private val methods: MutableList<Method> = mutableListOf()
    private val events: MutableList<String> = mutableListOf()

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
        methods += Method(name, parameterNames.toList())
    }

    fun event(type: String) {
        events += type
    }

    private fun addProperty(name: String, type: Type) {
        properties += Property(name, type)
    }

    internal fun build(): WebComponent =
        WebComponent(
            id = id,
            shadowMode = shadowMode,
            properties = properties.toList(),
            methods = methods.toList(),
            events = events.toList(),
            source = source
        )
}
