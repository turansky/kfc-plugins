package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

open class DevServerExtension {
    var root: String? = null

    internal val proxies = mutableListOf<ApplicationProxy>()

    fun proxy(action: ApplicationProxy.() -> Unit) {
        proxies.add(ApplicationProxy().apply(action))
    }
}

class ApplicationProxy {
    var source: Project? = null
    var port: Int = 0
}
