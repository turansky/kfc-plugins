package io.github.turansky.kfc.gradle.plugin

object Vite : Bundler(
    toolName = "vite",
    bin = "vite/bin/vite.js",
    dependencies = listOf(
        // https://www.npmjs.com/package/rolldown-vite
        Dependency(name = "vite", version = "npm:rolldown-vite@^7.2.4"),
    ),
) {

    // TODO: We need .mjs extension for now to enable connecting pure ESM plugins
    //  Until Kotlin isn't specifying `type: "module"` in generated `package.json`
    //   Ticket: https://youtrack.jetbrains.com/issue/KT-72680/KJS.-Specify-type-module-in-generated-package.json
    const val CONFIG_FILE: String = "vite.config.mjs"
}
