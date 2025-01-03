import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("me.bumiller.mol.compose.application")
}

kotlin {
    jvmToolchain(21)

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    sourceSets {

        val jvmMain by getting

        androidMain.dependencies {
            implementation(libs.android.splashscreen)
        }

        commonMain.dependencies {
            implementation(project(":model"))
            implementation(project(":data"))
            implementation(project(":ui"))
            implementation(project(":feature:onboarding"))
            implementation(project(":common-ui"))
            implementation(project(":network"))
            implementation(project(":settings"))
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

    }
}

compose.desktop.application {
    // Workaround according to https://github.com/JetBrains/compose-multiplatform/issues/3818#issuecomment-1795163561
    buildTypes.release.proguard {
        version.set("7.4.0")
    }

    mainClass = "me.bumiller.mol.MainKt"

    nativeDistributions {
        targetFormats(TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
        packageName = "me.bumiller.mol"
        packageVersion = "0.0.2"
    }
}
