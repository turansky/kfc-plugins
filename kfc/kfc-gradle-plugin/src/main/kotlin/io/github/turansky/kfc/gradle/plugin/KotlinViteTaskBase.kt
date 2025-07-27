package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation

sealed class KotlinViteTaskBase :
    DefaultTask() {

    private val jsPlatform: JsPlatform by lazy {
        JsPlatform.entries.first { name.startsWith(it.name) }
    }

    @get:Internal
    val compilation: KotlinJsIrCompilation by lazy {
        project.kotlinMainCompilation(jsPlatform)
    }
}
