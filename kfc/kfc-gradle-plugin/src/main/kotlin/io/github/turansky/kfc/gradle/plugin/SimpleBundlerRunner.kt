package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle
import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle.Companion.execAsync
import org.gradle.deployment.internal.DeploymentRegistry
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProjectModules

internal data class SimpleBundlerRunner(
    private val configuration: BundlerRunConfiguration,
) : BundlerRunner {

    private val npmProject: NpmProject
        get() = configuration.npmProject

    private val execOperations: ExecOperations
        get() = configuration.execOperations

    private val bundler: Bundler
        get() = configuration.bundler

    fun run() {
        if (configuration.continuous) {
            startNonBlocking()
        } else {
            execute()
        }
    }

    override fun start(): ExecAsyncHandle {
        return execOperations.execAsync(bundler.toolName) { execSpec ->
            configureExec(execSpec)
        }.start()
    }

    private fun startNonBlocking() {
        val deploymentRegistry = configuration.services.get(DeploymentRegistry::class.java)
        val deploymentHandle = deploymentRegistry.get(bundler.toolName, BundlerHandle::class.java)
        if (deploymentHandle == null) {
            deploymentRegistry.start(
                bundler.toolName,
                DeploymentRegistry.ChangeBehavior.BLOCK,
                BundlerHandle::class.java,
                this,
            )
        }
    }

    private fun execute() {
        execOperations.exec {
            configureExec(this)
        }
    }

    private fun configureExec(
        execFactory: ExecSpec,
    ) {
        val workingDir = npmProject.dir

        val modules = NpmProjectModules(workingDir.get().asFile)
        val scriptPath = modules.require(bundler.bin)

        execFactory.workingDir(workingDir)
        execFactory.executable(npmProject.nodeJs.executable.get())
        execFactory.args = listOf(scriptPath) + configuration.args
    }
}
