package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Provider

fun RegularFileProperty.copyIfChanged(
    workingDirectory: Provider<Directory>,
    fileSystemOperations: FileSystemOperations,
) {
    val original = get().asFile
    val copy = workingDirectory.get().file(original.name).asFile

    if (!copy.exists() || copy.readText() != original.readText()) {
        fileSystemOperations.copy {
            from(original)
            into(workingDirectory)
        }
    }
}
