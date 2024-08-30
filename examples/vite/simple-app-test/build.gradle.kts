plugins {
    `kotlin-jvm-conventions`
    `bundle-test-conventions`
}

dependencies {
    jsTestBundle(projects.examples.vite.simpleViteApp)
    jsTestBundle(projects.examples.vite.simpleWebpackApp)
}
