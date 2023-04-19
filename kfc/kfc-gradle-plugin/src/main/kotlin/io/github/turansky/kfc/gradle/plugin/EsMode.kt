package io.github.turansky.kfc.gradle.plugin

import org.jetbrains.kotlin.gradle.dsl.JsModuleKind

// check for Kotlin `1.8.10`
internal val ES_MODE_SUPPORTED: Boolean by lazy {
    JsModuleKind.values().any { it.kind == "es" }
}
