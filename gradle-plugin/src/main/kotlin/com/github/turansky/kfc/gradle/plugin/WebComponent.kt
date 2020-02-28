package com.github.turansky.kfc.gradle.plugin

data class WebComponent(
    val id: String,
    val properties: List<Property>,
    val events: List<String>,
    val source: String
) {
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
