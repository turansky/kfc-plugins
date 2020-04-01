package com.github.turansky.kfc.gradle.plugin

open class DevServerExtension {
    var root: String? = null

    internal val proxies = mutableListOf<ApplicationProxy>()

    fun proxy(action: ApplicationProxy.() -> Unit) {
        proxies.add(ApplicationProxy().apply(action))
    }
}

class ApplicationProxy {
    var source: String = "<undefined>"
    var port: Int = 0
}
