package com.github.turansky.kfc.gradle.plugin

internal object Momentjs {
    const val PATCH_NAME: String = "momentjs-locales-ignore"

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
