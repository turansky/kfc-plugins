[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/github/turansky/kfc/root/com.github.turansky.kfc.root.gradle.plugin/maven-metadata.xml.svg?label=plugin&logo=gradle)](https://plugins.gradle.org/plugin/com.github.turansky.kfc.root)
[![Kotlin](https://img.shields.io/badge/kotlin-1.3.72-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotlin/JS Fast Configuration

## Table of contents
* Plugins
  * [`webpack`](#webpack)
    * [Resources](#resources)
    * [Multiple outputs](#multiple-outputs)
  * [`library`](#library)
    * [NPM Dependencies](#npm-dependencies)
  * [`component`](#component)

## `webpack`

### Goal
* Non-static webpack configuration
* Make Kotlin `resources` content available for Webpack

### Task `patchWebpackConfig`
```Kotlin
plugins {
    kotlin("js") version "1.3.72" 
    id("com.github.turansky.kfc.webpack") version "0.9.1"
}

kotlin.target.browser()

tasks {
    patchWebpackConfig {
        // language=JavaScript
        patch("""
            config.output.libraryExport = ['com', 'example', 'app']
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

### Multiple outputs
```Kotlin
plugins {
    kotlin("js") version "1.3.72" 
    id("com.github.turansky.kfc.webpack") version "0.9.1"
}

kotlin.target.browser()

webpack {
    output {
        name = "content"
        root = "com.test.example.content"
    }

    output {
        name = "background"
        root = "com.test.example.background"
    }
}
```

Standard distribution directory used for output
 ```
 build/
  distributions/
    background.js
    content.js
```

[Test example](examples/multiple-output)

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
    kotlin("js") version "1.3.72" 
    id("com.github.turansky.kfc.library") version "0.9.1"
}
```

### NPM Dependencies
Generated `package.json` used to save NPM dependencies after publication in Maven repository.

If NPM dependencies are declared in Gradle, 
```Kotlin
dependencies {
    implementation(npm("style-loader", "1.1.3"))
    implementation(npm("css-loader", "3.4.2"))
}
```

then `library` plugin generate `package.json` and add it in `.jar` root.
```JSON
{
    "devDependencies": {
        "style-loader": "1.1.3",
        "css-loader": "3.4.2"
    }
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
    kotlin("js") version "1.3.72" 
    id("com.github.turansky.kfc.component") version "0.9.1"
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
