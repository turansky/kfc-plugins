[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/github/turansky/kfc/root/com.github.turansky.kfc.root.gradle.plugin/maven-metadata.xml.svg?label=plugin&logo=gradle)](https://plugins.gradle.org/plugin/com.github.turansky.kfc.root)
[![Kotlin](https://img.shields.io/badge/kotlin-1.3.61-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotlin Fast Configuration

## Table of contents
* Plugins
  * [`webpack`](#webpack)
  * [`library`](#library)
  * [`component`](#component)

## `webpack`

TBD

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
```Kotlin
plugins {
    kotlin("js") version "1.3.70" 
    id("com.github.turansky.kfc.library") version "0.0.36"
}
```

### Local testing/run
* `library` gradle extension
* Webpack target - `umd`

#### `build.gradle.kts`
```Kotlin
library {
    // Webpack - output.libraryExport = ["com", "example", "app"]
    root = "com.example.app"
}
```

## `component`

Apply [`webpack`](#webpack) plugin by default

### Goal
* Common distribution configuration for DCE/Webpack

### Decision
* `component` gradle extension
* Webpack target - `umd` 

#### `build.gradle.kts`
```Kotlin
plugins {
    kotlin("js") version "1.3.70" 
    id("com.github.turansky.kfc.component") version "0.0.36"
}

component {
    // DCE     - keep += "${jsProjectId}.com.example.app"
    // Webpack - output.libraryExport = ["com", "example", "app"]
    root = "com.example.app"
}
```

#### `App.kt`
```Kotlin
package com.example.app

class App {
    fun draw() { 
        // ...
    }
}
```

#### `index.html`
```HTML
<script src="component.js"></script>
<script>
    const app = new App()
    app.draw()
</script>
```
