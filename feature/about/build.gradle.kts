plugins {
    id("me.bumiller.civoris.compose.library")
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":ui"))
            implementation(project(":common-ui"))

            api(libs.webview)
        }
    }
}