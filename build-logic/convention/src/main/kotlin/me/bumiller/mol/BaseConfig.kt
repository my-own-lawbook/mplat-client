package me.bumiller.mol

import org.gradle.api.Project

/**
 * Configures the base android settings
 */
fun Project.androidConfig() = with(libraryExtension()) {
    namespace = NamespacePrefix + this@androidConfig.name
        .lowercase()
        .replace('-', '_')

    defaultConfig {
        compileSdk = CompileSdk
        minSdk = MinSdk

        testInstrumentationRunner = InstrumentationRunner
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

fun Project.kotlinConfig(isAndroid: Boolean = false) = with(kotlinExtension()) {
    jvm()

    if (isAndroid) {
        androidTarget()
    }
}