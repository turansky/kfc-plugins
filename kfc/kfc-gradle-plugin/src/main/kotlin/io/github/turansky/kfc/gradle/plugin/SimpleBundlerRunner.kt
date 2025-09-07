package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle
import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle.Companion.execAsync
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject

private const val VITE_BIN = "vite/bin/vite.js"

internal data class SimpleBundlerRunner(
    private val configuration: BundlerRunConfiguration,
) : BundlerRunner {

    private val npmProject: NpmProject
        get() = configuration.npmProject

    private val execOperations: ExecOperations
        get() = configuration.execOperations

    override fun start(): ExecAsyncHandle {
        return execOperations.execAsync("vite") { execSpec ->
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
            tool = VITE_BIN,
            args = configuration.args,
        )
    }
}
