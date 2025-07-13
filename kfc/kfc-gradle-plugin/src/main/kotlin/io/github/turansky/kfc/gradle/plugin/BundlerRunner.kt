package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.utils.processes.ExecAsyncHandle

internal interface BundlerRunner {
    fun start(): ExecAsyncHandle
    fun execute()
}
