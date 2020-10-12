plugins {
    kotlin("js")
}

kotlin.js {
    browser {
        dceTask {
            keep("kfc-plugins-react-optimizer.App")
        }
    }
}

dependencies {
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.124-kotlin-1.4.10")
}
