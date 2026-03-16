// TEMP
@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

@file:JsQualifier("import.meta.env")

import kotlin.js.JsName
import kotlin.js.JsQualifier

@JsName("VITE_BUILD_NAME")
external val BUILD_NAME: String

@JsName("VITE_BUILD_NUMBER")
external val BUILD_NUMBER: String
