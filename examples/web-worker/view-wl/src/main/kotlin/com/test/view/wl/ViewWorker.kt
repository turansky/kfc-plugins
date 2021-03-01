@file:Suppress("NOTHING_TO_INLINE")

package com.test.view.wl

import org.w3c.dom.Worker

@JsName("require")
external fun jsRequire(path: String): dynamic

inline fun createWorker(id: String): Worker {
    @Suppress("UNUSED_VARIABLE")
    val WorkerClass = jsRequire("worker-loader!$id.js").default
    return js("(new WorkerClass())")
}

fun ViewWorker(): Worker =
    createWorker("ww-worker-wl")
