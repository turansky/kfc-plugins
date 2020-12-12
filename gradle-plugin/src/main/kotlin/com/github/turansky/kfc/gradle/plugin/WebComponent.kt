package com.github.turansky.kfc.gradle.plugin

object WebComponent {

    fun wrap(
        moduleName: String,
        components: List<String>
    ): String =
        "import * as source from '$moduleName'\n\n" +
                components.joinToString("\n") {
                    val name = it.substringAfterLast(".")
                    "export const $name = source.$it"
                }
}
