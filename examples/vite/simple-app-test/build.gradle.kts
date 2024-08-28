plugins {
    `bundle-test-conventions`
}

dependencies {
    jsTestBundle(projects.examples.vite.simpleViteApp)
    jsTestBundle(projects.examples.vite.simpleWebpackApp)
}
