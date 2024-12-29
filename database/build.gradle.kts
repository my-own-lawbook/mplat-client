plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.mol.android.library)
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Room-specific dependencies
            implementation(libs.room.runtime)
            implementation(libs.sqlite.driver)
        }
    }
}

// Platform independent ksp-dependency for the room compiler
dependencies {
    add("ksp", libs.room.compiler)
}

// Location for the room database schema
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