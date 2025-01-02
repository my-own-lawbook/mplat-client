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
            implementation(project(":data"))
            implementation(project(":ui"))
            implementation(project(":common-ui"))
            implementation(project(":network"))
        }
    }
}