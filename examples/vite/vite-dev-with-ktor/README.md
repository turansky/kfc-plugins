## Start hot-reloading client + Ktor

#### KotlinJS recompilation on changes

```shell
cd ../../..
./gradlew examples:vite:vite-dev-with-ktor:vite-dev-app:jsDevelopmentExecutableCompileSync --continuous
```

#### Vite dev run

```shell
cd ../../..
./gradlew examples:vite:vite-dev-with-ktor:vite-dev-app:jsBrowserRun2
```

#### Ktor server run

```shell
cd ../../..
./gradlew examples:vite:vite-dev-with-ktor:ktor-app:run
```
