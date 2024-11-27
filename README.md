[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.turansky.kfc.library?logo=gradle)](https://plugins.gradle.org/plugin/io.github.turansky.kfc.library)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotlin/JS Fast Configuration

## `application`

### Usage

#### `build.gradle.kts`

```kotlin
plugins {
    id("io.github.turansky.kfc.application") version "12.2.0"
}
```

### Defaults

* Kotlin/JS
  * Target `es2015`
  * Granularity `per-file`

### Bundler

We use [Vite](https://vitejs.dev/)

#### Configuration

You can add your custom `vite.config.mjs` ([example](examples/vite/custom-config/vite.config.mjs))

#### Dev Server

1. Add `index.html` file with module script to JS
   entrypoint ([example](examples/vite/vite-dev/src/jsMain/resources/index.html))

2. Specify custom config file with `root` to `kotlin` ([example](examples/vite/vite-dev/vite.config.mjs))

3. Start a dev server, run `jsViteRun` task ([example](examples/vite/vite-dev))

### Source Maps

Enable source maps generation:

```properties
kfc.source.maps=true
```
