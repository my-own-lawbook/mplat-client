plugins {
    id("me.bumiller.mol.android.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":network"))
            implementation(project(":database"))
        }

        androidMain.dependencies {
            implementation(libs.android.work)
            implementation(libs.koin.android.work)
        }
    }
}