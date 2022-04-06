package kfc.import

import kotlin.js.Promise

@JsName("__dynamic_import__")
external fun <T : Any> import(
    path: String,
): Promise<Module<T>>
