plugins {
    alias(libs.plugins.kfc.application)
}

dependencies {
    jsMainImplementation(projects.examples.vite.simpleLibrary)
}
