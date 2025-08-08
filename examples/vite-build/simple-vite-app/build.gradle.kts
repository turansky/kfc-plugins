plugins {
    alias(kfc.plugins.application)
}

bundlerEnvironment {
    set("BUILD_NAME", "Frodo Baggins")
    set("BUILD_NUMBER", "212374918234198245123451234")
}

dependencies {
    commonMainImplementation(projects.examples.viteBuild.simpleLibrary)
}
