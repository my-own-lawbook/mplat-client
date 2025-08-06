import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "me.bumiller.civoris.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    compileOnly(libs.android.tools.build.gradle)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "me.bumiller.civoris.android.library"
            implementationClass = "AndroidLibraryModule"
            version = "1.0.0"
        }

        register("androidApplication") {
            id = "me.bumiller.civoris.android.application"
            implementationClass = "AndroidApplicationModule"
            version = "1.0.0"
        }

        register("kotlinLibrary") {
            id = "me.bumiller.civoris.kotlin.library"
            implementationClass = "KotlinLibraryModule"
            version = "1.0.0"
        }

        register("composeLibrary") {
            id = "me.bumiller.civoris.compose.library"
            implementationClass = "ComposeLibraryModule"
            version = "1.0.0"
        }
        register("composeApplication") {
            id = "me.bumiller.civoris.compose.application"
            implementationClass = "ComposeApplicationModule"
            version = "1.0.0"
        }
    }
}