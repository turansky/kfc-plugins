[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.turansky.kfc.library?logo=gradle)](https://plugins.gradle.org/plugin/io.github.turansky.kfc.library)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotlin/JS Fast Configuration

## `application`

* Kotlin/JS
  * Target `es2015`
  * Compilation `per-file`
* Bundler - [Vite](https://vitejs.dev/)

### Usage

#### `build.gradle.kts`

```kotlin
plugins {
    id("io.github.turansky.kfc.application") version "11.5.0"
}
```

### Configuration

You can add your custom [`vite.config.js` file](examples/vite/custom-config/vite.config.js)

### Source Maps

Enable source maps generation:

```properties
kfc.source.maps=true
```

### Legacy build with `Webpack`

Opt-out to a Webpack build:

```properties
kfc.bundler=webpack
```

Output granularity `whole-program`:

```properties
kfc.legacy=true
```
