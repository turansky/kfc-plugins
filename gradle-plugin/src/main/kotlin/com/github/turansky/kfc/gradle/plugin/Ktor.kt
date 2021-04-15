package com.github.turansky.kfc.gradle.plugin

internal fun PatchWebpackConfig.patchKtorDependencies() {
    val ignoreFile = project.rootDir.resolve(".ktorignore")
        .takeIf { it.exists() }
        ?: return

    val aliases = ignoreFile
        .readLines()
        .asSequence()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .joinToString(",\n") { "'$it': false" }

    patch(
        """
    config.resolve.alias = {
        $aliases
    }    
    """
    )
}
