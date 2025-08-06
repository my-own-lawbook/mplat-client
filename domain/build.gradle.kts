plugins {
    alias(libs.plugins.civoris.kotlin.library)
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":model"))
        implementation(project(":data"))
    }
}