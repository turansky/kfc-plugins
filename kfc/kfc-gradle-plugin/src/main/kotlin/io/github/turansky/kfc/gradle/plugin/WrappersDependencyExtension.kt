package io.github.turansky.kfc.gradle.plugin

interface WrappersDependencyExtension {
    operator fun invoke(name: String): String
}

internal open class WrappersDependencyExtensionImpl :
    WrappersDependencyExtension {
    override fun invoke(name: String): String {
        return "org.jetbrains.kotlin-wrappers:kotlin-$name"
    }
}
