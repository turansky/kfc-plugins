@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

import kotlin.js.JsName
import kotlin.js.JsQualifier

@JsQualifier("import.meta.env")
@JsName("VITE_BUILD_NAME")
external val BUILD_NAME: String

@JsQualifier("import.meta.env")
@JsName("VITE_BUILD_NUMBER")
external val BUILD_NUMBER: String
