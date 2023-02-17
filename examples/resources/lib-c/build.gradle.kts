plugins {
    id("io.github.turansky.kfc.library")
}

dependencies {
    jsMainImplementation(npmv("style-loader"))
    jsMainImplementation(npmv("css-loader"))
}
