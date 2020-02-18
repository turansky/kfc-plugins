package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.DomainObjectCollection
import org.gradle.kotlin.dsl.withType

// https://github.com/gradle/gradle/issues/12266
inline fun <reified S : Any> DomainObjectCollection<in S>.configureEach(
    noinline action: S.() -> Unit
) {
    withType().configureEach(action)
}
