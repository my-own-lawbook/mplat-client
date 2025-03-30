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
            implementation(project(":feature:about"))
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
 * 2. Copies the license report to the ':feature:about' module, where it is needed
 * 3. Deletes the originally generated reports
 */
tasks.create("civorisLicenseReport") {
    dependsOn("licenseReleaseReport")

    outputs.upToDateWhen { false }

    doFirst {
        val targetFile = project.file(
            "./../feature/about/src/commonMain/composeResources/files/license_report.html",
            PathValidation.NONE
        )
        val licenseHtmlReportFile =
            project.file("./src/androidMain/assets/open_source_licenses.html")
        licenseHtmlReportFile.copyTo(targetFile, true)

        val commonMainAssetsDirectory = project.file("./src/androidMain/assets/")
        val mainSourceSetDirectory = project.file("./src/main/")
        mainSourceSetDirectory.deleteRecursively()
        commonMainAssetsDirectory.deleteRecursively()
    }
}

/*
 * Ensures the following:
 *
 * 1. 'licenseReleaseReport' is always run, and never 'UP_TO_DATE'
 * 2. 'civorisLicenseReport' is ran before any 'install*', 'assemble*' and 'bundle*' task
 */
afterEvaluate {
    //noinspection WrongGradleMethod
    tasks.forEach { task ->
        if (task.name == "licenseReleaseReport") {
            task.outputs.upToDateWhen { false }
        }
        buildingTasksPrefixes.forEach { prefix ->
            if (task.name.startsWith(prefix)) {
                task.dependsOn("civorisLicenseReport")
            }
        }
    }
}

private val iconFileIco = project.file("./src/jvmMain/resources/icon.ico")
private val iconFilePng = project.file("./src/jvmMain/resources/icon.png")
private val appLicenseFile = project.file("./../LICENSE")

private val appVersion = "0.0.7"
private val appName = "Civoris"
private val appDescription =
    "Manage rules and conventions by creating a structured dictionary of guidelines! Collaborate with others and manage access, visibility and more."
private val appVendor = "Simon Bumiller"
private val appDebMaintainer = "simon@bumiller.me"

compose.desktop.application {
    // Args as described in https://github.com/KevinnZou/compose-webview-multiplatform/blob/main/README.desktop.md
    jvmArgs("--add-opens", "java.jvm/sun.awt=ALL-UNNAMED")
    jvmArgs("--add-opens", "java.jvm/java.awt.peer=ALL-UNNAMED")

    // Workaround according to https://github.com/JetBrains/compose-multiplatform/issues/3818#issuecomment-1795163561
    buildTypes.release.proguard {
        version.set("7.4.0")
        configurationFiles.setFrom("proguard-rules.pro", "compose-desktop.pro")
    }

    mainClass = "me.bumiller.mol.MainKt"

    nativeDistributions {
        targetFormats(TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)

        packageVersion = appVersion
        packageName = appName
        description = appDescription
        licenseFile = appLicenseFile

        linux {
            iconFile.set(iconFilePng)
            debMaintainer = appDebMaintainer
        }
        windows {
            iconFile.set(iconFileIco)
            dirChooser = true
        }
    }
}
