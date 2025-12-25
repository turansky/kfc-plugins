package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

internal interface ExecOptions {
    val workingDir: Provider<Directory>
    val executable: String
    val args: List<String>
}
