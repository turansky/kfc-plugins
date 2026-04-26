plugins {
    id("kfcbuild.kotlin-jvm-conventions")
    id("kfcbuild.bundle-test-conventions")
}

dependencies {
    jsTestBundle(projects.examples.viteBuild.simpleViteApp)
}
