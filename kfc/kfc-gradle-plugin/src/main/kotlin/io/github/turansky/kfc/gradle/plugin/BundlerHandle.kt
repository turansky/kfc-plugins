package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.utils.processes.ExecAsyncHandle
import org.gradle.deployment.internal.Deployment
import org.gradle.deployment.internal.DeploymentHandle
import javax.inject.Inject

internal open class BundlerHandle
@Inject constructor(
    private val runner: BundlerRunner,
) : DeploymentHandle {

    private var process: ExecAsyncHandle? = null

    override fun isRunning(): Boolean =
        process?.isAlive() == true

    override fun start(deployment: Deployment) {
        process = runner.start()
    }

    override fun stop() {
        process?.abort()
    }
}
