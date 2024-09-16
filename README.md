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
    id("io.github.turansky.kfc.application") version "11.0.0"
}
```

## Vite

You can build your project with a fast and reliable bundler - Vite!

It is enabled by default. To opt-out for the default Webpack build, you can add the following property:

```properties
kfc.bundler=webpack
```

### Configuration

You can add your custom `vite.config.js`. For example:

```javascript
import { defineConfig, loadEnv } from 'vite'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '')

  return {
    build: {
      rollupOptions: {
        input: {
          // `ENTRY_PATH` is predefined in KFC
          // Default: `build/js/packages/<your-project-name>/kotlin/<your-executable>`
          'myApp': env.ENTRY_PATH,
        },
      },
    },
  }
})
```

### Source Maps

You can enable source maps generation setting the following property:

```properties
kfc.source.maps=true
```
