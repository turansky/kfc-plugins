plugins {
    id("seskarbuild.kotlin-jvm-conventions")
    id("seskarbuild.bundle-test-conventions")
}

dependencies {
    jsTestBundle(projects.examples.viteBuild.simpleViteApp)
}
