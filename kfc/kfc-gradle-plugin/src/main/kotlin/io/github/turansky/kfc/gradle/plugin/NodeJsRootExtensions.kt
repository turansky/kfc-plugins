package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.web.nodejs.BaseNodeJsRootExtension

internal fun Project.getNodeJsRootExtension(
    platform: JsPlatform,
): BaseNodeJsRootExtension {
    val extensionName = when (platform) {
        JsPlatform.js -> NodeJsRootExtension.EXTENSION_NAME
        JsPlatform.wasmJs -> WasmNodeJsRootExtension.EXTENSION_NAME
    }

    return rootProject.extensions.getByName<_>(extensionName)
}
