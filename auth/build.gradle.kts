plugins {
    id("me.bumiller.mol.kotlin.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":network"))
            implementation(project(":settings"))

            implementation(libs.ktor.client)
        }
    }
}