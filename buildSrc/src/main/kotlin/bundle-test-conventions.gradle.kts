val jsTestBundle = configurations.create("jsTestBundle")

val unpackBundle by tasks.registering(Sync::class) {
    jsTestBundle
        .allDependencies
        .asSequence()
        .filterIsInstance<ProjectDependency>()
        .map { it.dependencyProject }
        .toSet()
        .map {
            val tasks = it.tasks.named<Jar>("jsBundleProduction")

            from(tasks.map { jar -> zipTree(jar.archiveFile) }) {
                into(it.name)
            }
        }

    into(temporaryDir)
}

tasks.named<Test>("jvmTest") {
    dependsOn(unpackBundle)

    environment("BUNDLE_PATH", unpackBundle.get().destinationDir)
}
