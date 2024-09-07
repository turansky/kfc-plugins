[![CI Status](https://github.com/turansky/kfc-plugins/workflows/CI/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![CI Status](https://github.com/turansky/kfc-plugins/workflows/gradle%20plugin/badge.svg)](https://github.com/turansky/kfc-plugins/actions)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.turansky.kfc.library?logo=gradle)](https://plugins.gradle.org/plugin/io.github.turansky.kfc.library)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Kotlin/JS Fast Configuration

## `application`

* Kotlin/JS target - `es2015`
* Bundler - [Vite](https://vitejs.dev/)

#### `build.gradle.kts`

```kotlin
plugins {
    id("io.github.turansky.kfc.application") version "10.1.0"
}
```
