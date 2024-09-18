This addresses an issue with `per-file` compilation with Webpack

1. Just run

```shell
./gradlew build
```

2. Change "CUSTOM TEXT" from `Main.kt` to something else
3. Rebuild
4. See that compileSync file differs from the `dist`
