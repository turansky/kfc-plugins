package com.test.local

import js.import.Module
import js.import.import

// For dev server only
private fun main() {
    println("Local!")
    import<Module<*>>("ww-view-wl")
}
