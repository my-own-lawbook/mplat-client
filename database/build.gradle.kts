plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "me.bumiller.mol"

    defaultConfig {
        compileSdk = 34
        minSdk = 24
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    jvmToolchain(21)

    jvm("jvm")
    androidTarget()
}

kotlin {
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    sourceSets {

        commonMain.dependencies {
            implementation(libs.room.runtime)
            implementation(libs.koin)
            implementation(libs.sqlite.driver)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }

    }
}

dependencies {
    add("ksp", libs.room.compiler)
}

