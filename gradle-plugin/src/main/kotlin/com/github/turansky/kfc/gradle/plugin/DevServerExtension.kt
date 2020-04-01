package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider

open class DevServerExtension {
    var root: String? = null

    internal val proxies = mutableListOf<ApplicationProxy>()

    fun proxy(action: ApplicationProxy.() -> Unit) {
        proxies.add(ApplicationProxy().apply(action))
    }
}

class ApplicationProxy {
    var source: Project? = null
    var task: String = "run"

    val sourceTask: TaskProvider<Task>
        get() = source!!.tasks.named(task)

    var port: Int = 0
}
