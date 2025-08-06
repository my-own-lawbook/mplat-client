plugins {
    id("me.bumiller.civoris.android.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":model"))

            implementation(libs.settings)
            implementation(libs.settings.observable)
        }

        androidMain.dependencies {
            implementation(libs.android.crypto)
        }
    }
}