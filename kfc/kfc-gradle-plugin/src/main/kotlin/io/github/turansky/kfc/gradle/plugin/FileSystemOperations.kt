package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider

// WA for https://github.com/gradle/gradle/issues/1643
fun FileSystemOperations.syncFile(
    source: Provider<RegularFile>,
    target: Provider<Directory>,
    strategy: SyncFileStrategy = SyncFileStrategy.PRESERVE_TARGET,
) {
    val original = source.get().asFile
    val copy = target.get().file(original.name).asFile

    if (!original.exists()) {
        if (strategy == SyncFileStrategy.DELETE_TARGET) {
            delete {
                delete(copy)
            }
        }
    } else {
        if (!copy.exists() || copy.readText() != original.readText()) {
            copy {
                from(original)
                into(target)
            }
        }
    }
}

enum class SyncFileStrategy {
    PRESERVE_TARGET,
    DELETE_TARGET
}
