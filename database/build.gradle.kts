plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    jvm("desktop")
}

kotlin {
    sourceSets {

        commonMain.dependencies {
            implementation(libs.room.runtime)
        }

    }
}

dependencies {
    ksp(libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}