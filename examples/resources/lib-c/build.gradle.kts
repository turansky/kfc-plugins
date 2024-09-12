plugins {
    alias(kfc.plugins.library)
}

dependencies {
    jsMainImplementation(npmv("@preact/signals-core"))
}
