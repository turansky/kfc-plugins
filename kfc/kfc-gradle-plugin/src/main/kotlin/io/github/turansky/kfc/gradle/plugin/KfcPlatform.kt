package io.github.turansky.kfc.gradle.plugin

import java.util.*

enum class KfcPlatform(
    val js: Boolean = false,
    val wasmJs: Boolean = false,
) {
    JS(js = true),
    WASM_JS(wasmJs = true),
    WEB(js = true, wasmJs = true),

    ;

    companion object {
        private val MAP: Map<String, KfcPlatform> =
            entries.associateBy { it.name.lowercase(Locale.US).replace("_", "") }

        fun get(id: String?): KfcPlatform {
            id ?: return JS

            return MAP[id]
                ?: error("Unknown KFC platform with id: '$id'")
        }
    }
}
