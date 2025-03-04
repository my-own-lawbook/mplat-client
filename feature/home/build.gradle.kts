plugins {
    id("me.bumiller.mol.compose.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":sync"))
            implementation(project(":common-ui"))
            implementation(project(":feature:profile"))
            implementation(project(":feature:dashboard"))
        }
    }
}