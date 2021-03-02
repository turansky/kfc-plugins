@file:Suppress("NOTHING_TO_INLINE")

package com.test.worker

import org.w3c.dom.Worker

@JsName("require")
external fun jsRequire(path: String): dynamic

inline fun createWorker(id: String): Worker {
    @Suppress("UNUSED_VARIABLE")
    val WorkerClass = jsRequire(id)
    return js("(new WorkerClass())")
}
