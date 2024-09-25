[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.turansky.kfc.library?logo=gradle)](https://plugins.gradle.org/plugin/io.github.turansky.kfc.library)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotlin/JS Fast Configuration

## `application`

### Usage

#### `build.gradle.kts`

```kotlin
plugins {
    id("io.github.turansky.kfc.application") version "11.6.0"
}
```

### Requirements

* Kotlin/JS
  * Target `es2015`
  * Granularity `per-file`

### Bundler

We use [Vite](https://vitejs.dev/)

#### Configuration

You can add your custom `vite.config.js` ([example](examples/vite/custom-config/vite.config.js))

##### Legacy build with `Webpack`

Opt-out to a Webpack build:

```properties
kfc.bundler=webpack
```

### Source Maps

Enable source maps generation:

```properties
kfc.source.maps=true
```
