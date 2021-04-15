package com.github.turansky.kfc.gradle.plugin

internal object Momentjs {
    val IGNORE_LOCALES_FLAG: BooleanProperty = BooleanProperty("kfc.ignore.momentjs.locales")

    // language=JavaScript
    val IGNORE_LOCALES_PATCH: String = """
       const { IgnorePlugin } = require('webpack')
       config.plugins.push(
         new IgnorePlugin({
           resourceRegExp: /^\.\/locale${'$'}/, 
           contextRegExp: /moment${'$'}/,
         })
       )
    """.trimIndent()
}
