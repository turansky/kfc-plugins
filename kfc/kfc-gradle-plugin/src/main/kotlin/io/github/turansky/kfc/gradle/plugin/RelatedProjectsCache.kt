package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project

class RelatedProjectsCache {
    private val map: MutableMap<Project, Set<Project>> = mutableMapOf()

    fun getOrPut(
        key: Project,
        defaultValue: () -> Set<Project>,
    ): Set<Project> =
        map.getOrPut(key, defaultValue)
}
