package com.github.turansky.kfc.gradle.plugin

internal object Momentjs {
    val IGNORE_LOCALES_FLAG: BooleanProperty = BooleanProperty("kfc.ignore.momentjs.locales")

    // language=Kotlin
    val IGNORE_LOCALES_PATCH: String = """
       const { IgnorePlugin } = require('webpack')
            config.plugins.push(
                new IgnorePlugin(/^\.\/locale${'$'}/, /moment${'$'}/),
            ) 
    """.trimIndent()
}
