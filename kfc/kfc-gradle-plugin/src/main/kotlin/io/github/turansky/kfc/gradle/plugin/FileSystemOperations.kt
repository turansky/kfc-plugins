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
    val original = source.get().asFile
    val target = destination.get().file(original.name).asFile

    if (!original.exists()) {
        if (strategy == SyncFileStrategy.OPTIONAL_SOURCE) {
            delete {
                delete(target)
            }
        }
    } else {
        if (!target.exists() || target.readText() != original.readText()) {
            copy {
                from(original)
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
