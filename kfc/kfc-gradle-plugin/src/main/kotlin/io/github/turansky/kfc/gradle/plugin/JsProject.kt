package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProjectModules

internal class JsProject(
    nodeJsExecutable: String,
    private val npmProject: NpmProject,
) {
    val workingDir: Provider<Directory>
        get() = npmProject.dir

    val executable: String =
        nodeJsExecutable

    fun scriptPath(path: String): String =
        NpmProjectModules(workingDir.get().asFile)
            .require(path)
}
