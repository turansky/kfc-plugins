package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProjectModules

internal class BundlerExecOptions(
    private val npmProject: NpmProject,
    private val bundler: Bundler,
    private val bundlerArgs: Array<out String>,
) : ExecOptions {
    override val workingDir: Provider<Directory>
        get() = npmProject.dir

    override val executable: String
        get() {
            @Suppress("ERROR_SUPPRESSION", "INVISIBLE_REFERENCE")
            return npmProject.nodeExecutable
        }

    private val scriptPath: String
        get() = NpmProjectModules(workingDir.get().asFile)
            .require(bundler.bin)

    override val args: List<String>
        get() = listOf(scriptPath) + bundlerArgs
}