package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

internal class BundlerExecOptions(
    private val project: JsProject,
    private val bundler: Bundler,
    private val bundlerArgs: Array<out String>,
) : ExecOptions {
    override val workingDir: Provider<Directory>
        get() = project.workingDir

    override val executable: String
        get() = project.executable

    private val scriptPath: String
        get() = project.scriptPath(bundler.bin)

    override val args: List<String>
        get() = listOf(scriptPath) + bundlerArgs
}