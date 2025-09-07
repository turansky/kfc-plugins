package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.kotlin.dsl.property
import org.gradle.process.ExecOperations
import org.jetbrains.kotlin.gradle.targets.js.NpmPackageVersion
import org.jetbrains.kotlin.gradle.targets.js.npm.RequiresNpmDependencies
import javax.inject.Inject

// https://www.npmjs.com/package/vite
private val VITE = NpmPackageVersion(name = "vite", version = "^7.1.4")

// https://www.npmjs.com/package/rollup-plugin-sourcemaps
private val ROLLUP_PLUGIN_SOURCEMAPS = NpmPackageVersion(name = "rollup-plugin-sourcemaps", version = "^0.6.3")

abstract class KotlinViteTask :
    KotlinViteTaskBase(),
    RequiresNpmDependencies {

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    @get:Inject
    protected abstract val execOperations: ExecOperations

    @Input
    val mode: Property<ViteMode> =
        objectFactory.property<ViteMode>()
            .convention(ViteMode.PRODUCTION)

    @get:Internal
    override val requiredNpmDependencies =
        setOf(VITE, ROLLUP_PLUGIN_SOURCEMAPS)

    @get:Internal
    abstract val isContinuous: Boolean

    protected fun vite(
        vararg args: String,
    ) {
        val configuration = BundlerRunConfiguration(
            npmProject = npmProject,
            execOperations = execOperations,
            services = services,
            bundler = Vite,
            args = args.toList(),
            continuous = isContinuous,
        )

        SimpleBundlerRunner(configuration).run()
    }
}
