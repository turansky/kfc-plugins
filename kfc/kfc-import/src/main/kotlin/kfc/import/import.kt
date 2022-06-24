package kfc.import

import kotlin.js.Promise

external fun <T : Any> import(
    path: String,
): Promise<T>
