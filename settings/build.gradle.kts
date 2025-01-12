plugins {
    id("me.bumiller.mol.android.library")
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