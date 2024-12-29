plugins {
    alias(libs.plugins.mol.kotlin.library)
}

kotlin {
    jvmToolchain(21)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":database"))
        implementation(project(":model"))
    }
}