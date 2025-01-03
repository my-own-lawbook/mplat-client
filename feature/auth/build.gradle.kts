plugins {
    id("me.bumiller.mol.compose.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":model"))
            implementation(project(":settings"))
            implementation(project(":ui"))
            implementation(project(":common-ui"))
            implementation(project(":network"))
        }
    }
}