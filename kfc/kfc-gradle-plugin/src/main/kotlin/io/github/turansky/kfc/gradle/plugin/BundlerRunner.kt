package io.github.turansky.kfc.gradle.plugin

import org.gradle.process.internal.ExecHandle

interface BundlerRunner {
    fun start(): ExecHandle
    fun execute()
}
