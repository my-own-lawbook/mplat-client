plugins {
    id("me.bumiller.mol.compose.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":ui"))
            implementation(project(":settings"))
            implementation(project(":common-ui"))
            implementation(project(":domain"))
            implementation(project(":sync"))
            implementation(project(":model"))
            implementation(project(":data"))
        }
    }
}