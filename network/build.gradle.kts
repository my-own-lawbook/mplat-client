plugins {
    id("me.bumiller.mol.kotlin.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client)
        }
    }
}