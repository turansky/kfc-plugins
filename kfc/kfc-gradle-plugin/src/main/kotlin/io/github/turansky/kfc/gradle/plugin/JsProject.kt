package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProjectModules

internal class JsProject(
    private val npmProject: NpmProject,
) {
    val workingDir: Provider<Directory>
        get() = npmProject.dir

    val executable: String
        get() {
            @Suppress("ERROR_SUPPRESSION", "INVISIBLE_REFERENCE")
            return npmProject.nodeExecutable
        }

    fun scriptPath(path: String): String =
        NpmProjectModules(workingDir.get().asFile)
            .require(path)
}
