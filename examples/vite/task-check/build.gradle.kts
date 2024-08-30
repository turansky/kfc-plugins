import io.github.turansky.kfc.gradle.plugin.KotlinViteTask

plugins {
    alias(libs.plugins.kfc.application)
}

dependencies {
    jsMainImplementation(projects.examples.vite.simpleLibrary)
}

val viteBuild by tasks.creating(KotlinViteTask::class) {
    dependsOn(tasks.compileProductionExecutableKotlinJs)

    doLast {
        println("Building with Vite...")
    }
}

tasks.build {
    dependsOn(viteBuild)
}
