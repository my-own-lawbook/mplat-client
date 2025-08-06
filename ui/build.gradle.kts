plugins {
    id("me.bumiller.civoris.compose.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":model"))
            implementation(project(":common-ui"))
        }
    }
}