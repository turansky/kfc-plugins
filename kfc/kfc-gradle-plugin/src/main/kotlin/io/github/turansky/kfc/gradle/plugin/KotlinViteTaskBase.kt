package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmProject
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject

sealed class KotlinViteTaskBase :
    DefaultTask() {

    @get:Internal
    protected val jsPlatform: JsPlatform =
        JsPlatform.entries.first { name.startsWith(it.name) }

    @Transient
    @Internal
    protected val compilation: KotlinJsIrCompilation =
        project.kotlinMainCompilation(jsPlatform)

    @Internal
    protected val npmProject: NpmProject =
        compilation.npmProject
}
