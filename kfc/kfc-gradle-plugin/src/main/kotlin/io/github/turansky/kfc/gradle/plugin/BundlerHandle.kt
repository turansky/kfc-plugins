package io.github.turansky.kfc.gradle.plugin

import org.gradle.deployment.internal.Deployment
import org.gradle.deployment.internal.DeploymentHandle
import org.gradle.process.internal.ExecHandle
import javax.inject.Inject

internal open class BundlerHandle
@Inject constructor(
    private val runner: BundlerRunner,
) : DeploymentHandle {

    private var process: ExecHandle? = null

    override fun isRunning(): Boolean =
        process != null

    override fun start(deployment: Deployment) {
        process = runner.start()
    }

    override fun stop() {
        process?.abort()
    }
}
