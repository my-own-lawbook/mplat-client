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
            implementation(libs.koin.android.work)
        }

        commonMain.dependencies {
            implementation(project(":model"))
            implementation(project(":data"))
            implementation(project(":ui"))
            implementation(project(":feature:onboarding"))
            implementation(project(":feature:home"))
            implementation(project(":feature:dashboard"))
            implementation(project(":feature:auth"))
            implementation(project(":common-ui"))
            implementation(project(":auth"))
            implementation(project(":network"))
            implementation(project(":settings"))
            implementation(project(":database"))
            implementation(project(":domain"))
            implementation(project(":sync"))

            // Workaround, see https://github.com/juliansteenbakker/flutter_secure_storage/issues/748#issuecomment-2505862197
            implementation(libs.spotbugs)
            implementation(libs.errorprone)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

    }
}

private val iconFileIco = project.file("./src/jvmMain/resources/icon.ico")
private val iconFilePng = project.file("./src/jvmMain/resources/icon.png")

private val appVersion = "0.0.4"

compose.desktop.application {
    // Workaround according to https://github.com/JetBrains/compose-multiplatform/issues/3818#issuecomment-1795163561
    buildTypes.release.proguard {
        version.set("7.4.0")
        configurationFiles.setFrom("proguard-rules.pro")
    }

    mainClass = "me.bumiller.mol.MainKt"

    nativeDistributions {
        targetFormats(TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)

        packageVersion = appVersion

        linux {
            iconFile.set(iconFileIco)
            this.debMaintainer
        }
        windows {
            iconFile.set(iconFilePng)
        }
        packageName = "me.bumiller.mol"
    }
}
