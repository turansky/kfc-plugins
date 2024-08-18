[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.turansky.kfc.library?logo=gradle)](https://plugins.gradle.org/plugin/io.github.turansky.kfc.library)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.10-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotlin/JS Fast Configuration

## Table of contents
* Plugins
  * [`webpack`](#webpack)
    * [Resources](#resources)
  * [`library`](#library)

## `webpack`

### Goal
* Non-static webpack configuration
* Make Kotlin `resources` content available for Webpack

### Task `patchBundlerConfig`

```kotlin
plugins {
  kotlin("multiplatform") version "2.0.10"
  id("io.github.turansky.kfc.webpack") version "8.18.0"
}

kotlin.js {
    browser()
}

tasks {
  patchBundlerConfig {
        // language=JavaScript
        patch(
            """
            config.output.library.export = ['com', 'example', 'app']
        """)
    }
}
```

#### Attention
`webpack.config.d` directory will be used as **temp**.
Add following `.gitignore` instruction to exclude directory from Git:
```
webpack.config.d/
```

## `library`

Apply [`webpack`](#webpack) plugin by default

### Goal
* Modularity

### Decision

* Kotlin/JS target - `es2015`

#### `build.gradle.kts`

```kotlin
plugins {
  kotlin("multiplatform") version "2.0.10"
  id("io.github.turansky.kfc.library") version "8.18.0"
}
```
