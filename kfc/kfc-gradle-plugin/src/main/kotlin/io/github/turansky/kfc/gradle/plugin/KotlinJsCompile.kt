package io.github.turansky.kfc.gradle.plugin

import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.io.File

// TODO: remove after migration
@Suppress("DEPRECATION")
fun Kotlin2JsCompile.getOutputFile(): File =
    outputFileProperty.get()
