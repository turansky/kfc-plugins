import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

plugins {
    kotlin("js")
}

kotlin.js {
    browser {
        dceTask {
            keep("kfc-plugins-react-optimizer.App")
        }
    }
}

tasks.named<KotlinJsCompile>("compileKotlinJs") {
    kotlinOptions.moduleKind = "commonjs"
}

dependencies {
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.121-kotlin-1.4.10")
}
