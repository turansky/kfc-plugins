plugins {
    kotlin("js")
    id("com.github.turansky.kfc.definitions")
}

kotlin.js {
    browser()
    binaries.executable()
}
