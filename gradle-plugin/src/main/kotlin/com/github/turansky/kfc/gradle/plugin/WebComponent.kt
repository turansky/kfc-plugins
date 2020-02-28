package com.github.turansky.kfc.gradle.plugin

import java.io.Serializable

data class WebComponent(
    private val id: String,
    private val properties: List<Property>,
    private val events: List<String>,
    private val source: String
) : Serializable {
    // language=JavaScript
    fun toCode(sourceModuleName: String): String = """
        |import * as source from '$sourceModuleName'
        |
        |export const $name = source.$source    
    """.trimMargin()

    val name: String = source.substringAfterLast(".")
    val sourceRoot: String = source.substringBeforeLast(".")

    data class Property(
        val name: String,
        val type: Type
    ) {
        enum class Type {
            RW, RO, WO
        }
    }
}
