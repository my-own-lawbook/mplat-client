package me.bumiller.mol

import org.gradle.api.Project

/**
 * Configures the base android settings
 */
fun Project.androidConfig() = with(libraryExtension()) {
    namespace = Namespace

    defaultConfig {
        compileSdk = CompileSdk
        minSdk = MinSdk

        testInstrumentationRunner = InstrumentationRunner
    }
}

fun Project.kotlinConfig(isAndroid: Boolean = false) = with(kotlinExtension()) {
    jvm()

    if (isAndroid) {
        androidTarget()
    }
}