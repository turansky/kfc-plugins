package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering

class BundlePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val configuration = Vite.js
    }
}
