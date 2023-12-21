package com.test.view

import AAA
import BBB
import CCC
import js.import.import
import js.promise.Promise

fun main() {
    Api
}

object Api {
    fun App(): String = AAA() + BBB() + CCC()

    fun json(): Promise<AppData> = import("app.json")
}

external interface AppData {
    val app: String
}
