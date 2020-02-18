package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.tasks.TaskCollection
import org.gradle.kotlin.dsl.withType

// https://github.com/gradle/gradle/issues/12266
internal inline fun <reified S : Task> TaskCollection<in S>.configureEach(action: Action<S>) {
    withType<S>().configureEach(action)
}
