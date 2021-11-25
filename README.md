[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/github/turansky/kfc/root/com.github.turansky.kfc.root.gradle.plugin/maven-metadata.xml.svg?label=plugin&logo=gradle)](https://plugins.gradle.org/plugin/com.github.turansky.kfc.root)
[![Kotlin](https://img.shields.io/badge/kotlin-1.6.0-blue.svg?logo=kotlin)](http://kotlinlang.org)

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

### Task `patchWebpackConfig`

```kotlin
plugins {
    kotlin("js") version "1.6.0"
    id("com.github.turansky.kfc.webpack") version "4.51.0"
}

kotlin.js {
    browser()
}

tasks {
    patchWebpackConfig {
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

### Resources
By default `webpack` plugin add `src/main/resources` directory of: 
* Current subproject
* Dependency subprojects 

as Webpack `modules`.

## `library`

Apply [`webpack`](#webpack) plugin by default

### Goal
* Fast build
* Modularity

### Decision
* Disable DCE
* Disable Webpack build
* Kotlin/JS target - `commonjs` 

#### `build.gradle.kts`

```kotlin
plugins {
    kotlin("js") version "1.6.0"
    id("com.github.turansky.kfc.library") version "4.51.0"
}
```
