plugins {
    id("io.github.turansky.kfc.library")
    id("io.github.turansky.kfc.wrappers")
}

dependencies {
    jsMainImplementation(wrappers("browser"))
}
