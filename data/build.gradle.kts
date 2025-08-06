plugins {
    alias(libs.plugins.civoris.android.library)
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":model"))
        implementation(project(":network"))
        implementation(project(":settings"))
        implementation(project(":database"))
    }
}