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
) {
    val sourceFile = source.get().asFile
    val targetFile = destination.get().file(sourceFile.name).asFile

    if (strategy == SyncFileStrategy.OPTIONAL_SOURCE && !sourceFile.exists()) {
        delete {
            delete(targetFile)
        }
        return
    }

    require(sourceFile.exists()) {
        "Unable to sync nonexistent file: `$sourceFile`"
    }

    if (targetFile.exists() && hasEqualContent(sourceFile, targetFile)) {
        return
    }

    copy {
        from(sourceFile)
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
