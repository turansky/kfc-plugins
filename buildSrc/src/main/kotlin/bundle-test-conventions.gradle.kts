val jsTestBundle = configurations.create("jsTestBundle")

val unpackBundle by tasks.registering(Sync::class) {
    val bundleProjects = jsTestBundle
        .allDependencies
        .mapNotNull { project.rootProject.allprojects.find { project -> it.name == project.name } }

    for (bundleProject in bundleProjects) {
        val bundleTask = bundleProject.tasks.named<Jar>("jsBundleProduction")
        val bundle = bundleTask.map { task ->
            zipTree(task.archiveFile)
                .matching { exclude("META-INF/") }
        }

        from(bundle) {
            into(bundleProject.name)
        }
    }

    into(temporaryDir)
}

tasks.named<Test>("jvmTest") {
    dependsOn(unpackBundle)

    environment("BUNDLE_PATH", unpackBundle.get().destinationDir)
}
