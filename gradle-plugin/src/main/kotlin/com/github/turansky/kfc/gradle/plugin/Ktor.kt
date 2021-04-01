package com.github.turansky.kfc.gradle.plugin

private const val NULL_ALIAS = "NULL_ALIAS"

// language=JavaScript
private const val NULL_ALIAS_CONTENT = "export const KY = 13"

internal fun PatchWebpackConfig.patchKtorDependencies() {
    val ignoreFile = project.rootDir.resolve(".ktorignore")
        .takeIf { it.exists() }
        ?: return

    val nullAlias = temporaryDir.resolve("null-alias.js")

    doFirst {
        nullAlias.writeText(NULL_ALIAS_CONTENT)
    }

    val aliases = ignoreFile
        .readLines()
        .asSequence()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .joinToString(",\n") { "'$it': $NULL_ALIAS" }

    patch(
        """
    const $NULL_ALIAS = '${nullAlias.absolutePath}'    
    config.resolve.alias = {
        $aliases
    }    
    """
    )
}
