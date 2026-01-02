package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import java.io.File

// WA for https://github.com/gradle/gradle/issues/1643
fun FileSystemOperations.syncFile(
    source: Provider<RegularFile>,
    destination: Provider<Directory>,
    strategy: SyncFileStrategy = SyncFileStrategy.REQUIRED_SOURCE,
    fallback: (() -> File)? = null,
) {
    val sourceFile = source.get().asFile
    val targetFile = destination.get().file(sourceFile.name).asFile

    if (strategy == SyncFileStrategy.OPTIONAL_SOURCE && !sourceFile.exists()) {
        delete {
            delete(targetFile)
        }
        return
    }

    val source = sourceFile.takeIf { it.exists() }
        ?: fallback?.invoke()

    requireNotNull(source) {
        "Unable to sync nonexistent file: `$sourceFile`"
    }

    if (targetFile.exists() && hasEqualContent(source, targetFile)) {
        return
    }

    copy {
        from(source)
        into(destination)
    }
}

private fun hasEqualContent(
    fileA: File,
    fileB: File,
): Boolean {
    return fileA.readBytes()
        .contentEquals(fileB.readBytes())
}

enum class SyncFileStrategy {
    REQUIRED_SOURCE,
    OPTIONAL_SOURCE,

    ;
}
