plugins {
    alias(kfc.plugins.application)
}

dependencies {
    jsMainImplementation(projects.examples.vite.simpleLibrary)
}
