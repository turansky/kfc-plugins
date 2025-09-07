package io.github.turansky.kfc.gradle.plugin

object Vite : Bundler(
    displayName = "Vite",
    toolName = "vite",
    bin = "vite/bin/vite.js",
) {

    // TODO: We need .mjs extension for now to enable connecting pure ESM plugins
    //  Until Kotlin isn't specifying `type: "module"` in generated `package.json`
    //   Ticket: https://youtrack.jetbrains.com/issue/KT-72680/KJS.-Specify-type-module-in-generated-package.json
    const val CONFIG_FILE: String = "vite.config.mjs"
}
