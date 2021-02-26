plugins {
    kotlin("js")
}

kotlin.js {
    moduleName = "ww-entity"

    browser()
    useCommonJs()
}
