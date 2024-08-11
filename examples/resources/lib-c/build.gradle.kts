plugins {
    alias(libs.plugins.kfc.library)
}

dependencies {
    jsMainImplementation(npmv("style-loader"))
    jsMainImplementation(npmv("css-loader"))
}
