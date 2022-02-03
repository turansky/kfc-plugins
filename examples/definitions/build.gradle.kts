plugins {
    kotlin("js")
    id("io.github.turansky.kfc.definitions")
}

kotlin.js {
    browser()
    binaries.executable()
}
