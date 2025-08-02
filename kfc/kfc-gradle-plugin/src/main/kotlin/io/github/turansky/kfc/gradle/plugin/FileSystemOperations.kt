package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider

// WA for https://github.com/gradle/gradle/issues/1643
fun FileSystemOperations.syncFile(
    source: Provider<RegularFile>,
    destination: Provider<Directory>,
    strategy: SyncFileStrategy = SyncFileStrategy.REQUIRED_SOURCE,
) {
    val sourceFile = source.get().asFile
    val targetFile = destination.get().file(sourceFile.name).asFile

    if (!sourceFile.exists()) {
        if (strategy == SyncFileStrategy.OPTIONAL_SOURCE) {
            delete {
                delete(targetFile)
            }
        }
    } else {
        if (!targetFile.exists() || targetFile.readText() != sourceFile.readText()) {
            copy {
                from(sourceFile)
                into(destination)
            }
        }
    }
}

enum class SyncFileStrategy {
    REQUIRED_SOURCE,
    OPTIONAL_SOURCE,

    ;
}
