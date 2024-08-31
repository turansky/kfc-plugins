plugins {
    alias(libs.plugins.kfc.application)
}

bundlerEnvironment {
    set("BUILD_NAME", "!!--Frodo--!!")
    set("BUILD_NUMBER", "212374918234198245123451234")
}

dependencies {
    jsMainImplementation(projects.examples.vite.simpleLibrary)
}
