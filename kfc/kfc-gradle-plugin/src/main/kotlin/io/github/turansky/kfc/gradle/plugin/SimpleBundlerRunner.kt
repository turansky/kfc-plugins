package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle
import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle.Companion.execAsync
import org.gradle.deployment.internal.DeploymentRegistry
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec

internal data class SimpleBundlerRunner(
    private val configuration: BundlerRunConfiguration,
) : BundlerRunner {
    private val execOperations: ExecOperations
        get() = configuration.execOperations

    fun run() {
        if (configuration.continuous) {
            startNonBlocking()
        } else {
            execute()
        }
    }

    override fun start(): ExecAsyncHandle {
        return execOperations.execAsync(configuration.id) { execSpec ->
            configureExec(execSpec)
        }.start()
    }

    private fun startNonBlocking() {
        val deploymentRegistry = configuration.services.get(DeploymentRegistry::class.java)
        val deploymentHandle = deploymentRegistry.get(configuration.id, BundlerHandle::class.java)
        if (deploymentHandle == null) {
            deploymentRegistry.start(
                configuration.id,
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
        val options = configuration.options

        execFactory.workingDir(options.workingDir)
        execFactory.executable(options.executable)
        execFactory.args = options.args
    }
}
