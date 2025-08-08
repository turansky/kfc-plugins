plugins {
    alias(kfc.plugins.library)
}

dependencies {
    commonMainImplementation(npmv("@preact/signals-core"))
}
