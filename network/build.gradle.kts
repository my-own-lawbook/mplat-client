plugins {
    id("me.bumiller.mol.android.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":settings"))
            implementation(project(":model"))

            implementation(libs.ktor.client)
            implementation(libs.ktor.contentnegotiation)
            implementation(libs.ktor.json)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.auth)
            implementation(libs.napier)
        }

        androidMain.dependencies {
            implementation(libs.ktor.okhttp)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.cio)
        }
    }
}