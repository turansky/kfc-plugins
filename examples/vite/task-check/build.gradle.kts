import io.github.turansky.kfc.gradle.plugin.KotlinViteTask

plugins {
    alias(libs.plugins.kfc.application)
}

dependencies {
    jsMainImplementation(projects.examples.vite.simpleLibrary)
}

val viteBuild by tasks.creating(KotlinViteTask::class) {
    outputDirectory = layout.buildDirectory.dir("my-vite-distribution")

    dependsOn(tasks.compileProductionExecutableKotlinJs)
}

tasks.build {
    dependsOn(viteBuild)
}
