plugins {
    id("me.bumiller.civoris.compose.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":sync"))
            implementation(project(":model"))
            implementation(project(":common-ui"))
            implementation(project(":feature:profile"))
            implementation(project(":feature:dashboard"))
        }
    }
}