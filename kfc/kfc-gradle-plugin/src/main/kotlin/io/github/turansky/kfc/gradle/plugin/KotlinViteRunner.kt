package io.github.turansky.kfc.gradle.plugin

import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec
import org.gradle.process.internal.ExecHandle
import org.gradle.process.internal.ExecHandleFactory
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject

private const val VITE_BIN = "vite/bin/vite.js"

internal data class KotlinViteRunner(
    val npmProject: NpmProject,
    val execHandleFactory: ExecHandleFactory,
    val execOperations: ExecOperations,
    val args: List<String>,
) : BundlerRunner {

    override fun start(): ExecHandle {
        val execFactory = execHandleFactory.newExec()
        configureExec(execFactory)
        val exec = execFactory.build()
        exec.start()
        return exec
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
            args = args,
        )
    }
}
