package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.kotlin.dsl.property
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class KotlinViteTask :
    KotlinViteTaskBase() {

    init {
        dependsOnNpmInstallTasks(jsPlatform)
    }

    @get:Inject
    protected abstract val objectFactory: ObjectFactory

    @get:Inject
    protected abstract val execOperations: ExecOperations

    @Input
    val mode: Property<ViteMode> =
        objectFactory.property<ViteMode>()
            .convention(ViteMode.PRODUCTION)

    @get:Internal
    abstract val isContinuous: Boolean

    protected fun vite(
        vararg args: String,
    ) {
        val options = BundlerExecOptions(
            npmProject = npmProject,
            bundler = Vite,
            bundlerArgs = args,
        )
        
        val configuration = BundlerRunConfiguration(
            bundler = Vite,
            options = options,
            execOperations = execOperations,
            services = services,
            continuous = isContinuous,
        )

        SimpleBundlerRunner(configuration).run()
    }
}
