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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
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

            implementation(libs.google.guava.workaround)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        androidInstrumentedTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.androidx.test.junit)
            implementation(libs.androidx.test.runner)
        }
    }
}

dependencies {
    add("ksp", libs.room.compiler)
}


room {
    schemaDirectory("$projectDir/schemas")
}

// Workaround for property 'inputDirectory' cannot be changed
// See https://slack-chats.kotlinlang.org/t/23153122/im-working-with-compose-multiplatform-project-for-android-an
tasks.whenTaskAdded {
    if (name.contains("copyRoomSchemasToAndroidTestAssetsDebugAndroidTest")) {
        enabled = false
    }
}