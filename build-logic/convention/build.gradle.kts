import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "me.bumiller.mol.buildlogic"

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
    compileOnly("com.android.tools.build:gradle:8.2.2")
    compileOnly("com.android.tools:common:31.7.3")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
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
            id = "me.bumiller.mol.android.library"
            implementationClass = "AndroidLibraryModule"
            version = "1.0.0"
        }

        register("androidApplication") {
            id = "me.bumiller.mol.android.application"
            implementationClass = "AndroidApplicationModule"
            version = "1.0.0"
        }

        register("kotlinLibrary") {
            id = "me.bumiller.mol.kotlin.library"
            implementationClass = "KotlinLibraryModule"
            version = "1.0.0"
        }

        register("composeLibrary") {
            id = "me.bumiller.mol.compose.library"
            implementationClass = "ComposeLibraryModule"
            version = "1.0.0"
        }
        register("composeApplication") {
            id = "me.bumiller.mol.compose.application"
            implementationClass = "ComposeApplicationModule"
            version = "1.0.0"
        }
    }
}