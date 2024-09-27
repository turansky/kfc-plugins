package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import javax.inject.Inject

class SingleCacheWebpackPlugin
@Inject constructor(
    objects: ObjectFactory,
) : SingleCacheBundlerPlugin(Webpack, objects) {
    override fun apply(target: Project) = with(target) {
        productionOutputDir = tasks.named<KotlinWebpack>(Webpack.productionTask).get()
            .outputDirectory

        developmentOutputDir = tasks.named<KotlinWebpack>(Webpack.developmentTask).get()
            .outputDirectory

        super.apply(target)
    }
}
