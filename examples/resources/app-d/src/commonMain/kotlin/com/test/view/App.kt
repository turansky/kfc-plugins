package com.test.view

import AAA
import BBB
import CCC
import js.import.import

fun main() {
    Api
}

object Api {
    fun App(): String = AAA() + BBB() + CCC()

    suspend fun json(): AppData = import("app.json")
}

external interface AppData {
    val app: String
}
