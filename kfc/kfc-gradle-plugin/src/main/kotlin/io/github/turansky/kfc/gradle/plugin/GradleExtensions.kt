package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.DomainObjectCollection
import org.gradle.api.Task
import org.gradle.kotlin.dsl.withType

// https://github.com/gradle/gradle/issues/9832
internal inline fun <reified S : Any> DomainObjectCollection<in S>.configureEach(
    noinline action: S.() -> Unit,
) {
    withType().configureEach(action)
}

internal inline fun <reified S : Task> DomainObjectCollection<in S>.disable() {
    configureEach<S> {
        enabled = false
    }
}

internal inline fun <reified S : Task> DomainObjectCollection<in S>.disable(
    noinline predicate: S.() -> Boolean,
) {
    configureEach<S> {
        if (predicate()) {
            enabled = false
        }
    }
}
