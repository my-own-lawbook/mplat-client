import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("me.bumiller.mol.compose.application")
    alias(libs.plugins.license)
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

        //noinspection WrongGradleMethod
        androidMain.dependencies {
            implementation(libs.android.splashscreen)
            implementation(libs.koin.android.work)
        }

        //noinspection WrongGradleMethod
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

// Tasks that are prefixed with one of these will generate a license report before building.
private val buildingTasksPrefixes = listOf("assemble", "bundle", "install")

/*
 * A task that perform the following steps:
 *
 * 1. Executes the license report task (html output)
 * 2. Deletes the generates report under src/main/assets/, because duplicate resources are not allowed
 */
tasks.create("civorisLicenseReport") {
    dependsOn("licenseReleaseReport")

    doFirst {
        val mainSourceSetDirectory = project.file("./src/main/")
        mainSourceSetDirectory.deleteRecursively()
    }
}

/*
 * Ensure that 'civorisLicenseReport' is run before
 */
afterEvaluate {
    //noinspection WrongGradleMethod
    tasks.forEach { task ->
        buildingTasksPrefixes.forEach { prefix ->
            if (task.name.startsWith(prefix)) {
                task.dependsOn("civorisLicenseReport")
            }
        }
    }
}

private val iconFileIco = project.file("./src/jvmMain/resources/icon.ico")
private val iconFilePng = project.file("./src/jvmMain/resources/icon.png")
private val licenseFile = project.file("./../LICENSE")

private val appVersion = "0.0.4"
private val appName = "Civoris"
private val appDescription =
    "Manage rules and conventions by creating a structured dictionary of guidelines! Collaborate with others and manage access, visibility and more."
private val appVendor = "Simon Bumiller"
private val appDebMaintainer = "simon@bumiller.me"

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
        packageName = appName
        description = appDescription
        licenseFile = licenseFile

        linux {
            iconFile.set(iconFilePng)
            debMaintainer = appDebMaintainer
        }
        windows {
            iconFile.set(iconFileIco)
        }
    }
}
