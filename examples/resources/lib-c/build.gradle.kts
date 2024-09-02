plugins {
    alias(libs.plugins.kfc.library)
}

dependencies {
    jsMainImplementation(npmv("@preact/signals-core"))
}
