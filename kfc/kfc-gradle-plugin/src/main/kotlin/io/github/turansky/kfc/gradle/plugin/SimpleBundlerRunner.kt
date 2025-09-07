package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle
import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle.Companion.execAsync
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject

internal data class SimpleBundlerRunner(
    private val configuration: BundlerRunConfiguration,
) : BundlerRunner {

    private val npmProject: NpmProject
        get() = configuration.npmProject

    private val execOperations: ExecOperations
        get() = configuration.execOperations

    private val bundler: Bundler
        get() = configuration.bundler

    override fun start(): ExecAsyncHandle {
        return execOperations.execAsync(bundler.toolName) { execSpec ->
            configureExec(execSpec)
        }.start()
    }

    override fun execute() {
        execOperations.exec {
            configureExec(this)
        }
    }

    private fun configureExec(
        execFactory: ExecSpec,
    ) {
        npmProject.useTool(
            exec = execFactory,
            tool = bundler.bin,
            args = configuration.args,
        )
    }
}
