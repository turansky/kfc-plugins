package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.named
import javax.inject.Inject

class SingleCacheVitePlugin
@Inject constructor(
    objects: ObjectFactory,
) : SingleCacheBundlerPlugin(Vite, objects) {
    override fun apply(target: Project) = with(target) {
        productionOutputDir = tasks.named<KotlinViteTask>(Vite.productionTask).get()
            .outputDirectory

        developmentOutputDir = tasks.named<KotlinViteTask>(Vite.developmentTask).get()
            .outputDirectory

        super.apply(target)
    }
}
