plugins {
    id("me.bumiller.civoris.android.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":network"))
            implementation(project(":model"))
            implementation(project(":database"))
        }

        androidMain.dependencies {
            implementation(libs.android.work)
            implementation(libs.koin.android.work)
            implementation(libs.uuid)
        }
    }
}