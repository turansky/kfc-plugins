package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Provider

// WA for https://github.com/gradle/gradle/issues/1643
fun FileSystemOperations.copyIfChanged(
    file: RegularFileProperty,
    destination: Provider<Directory>,
) {
    val original = file.get().asFile
    val copy = destination.get().file(original.name).asFile

    if (!copy.exists() || copy.readText() != original.readText()) {
        copy {
            from(original)
            into(destination)
        }
    }
}
