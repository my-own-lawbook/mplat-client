package me.bumiller.mol

import com.android.build.api.dsl.Packaging
import org.gradle.api.Project

private fun Packaging.defaultPackaging() {
    resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
        merges += "META-INF/LICENSE.md"
        merges += "META-INF/LICENSE-notice.md"
    }
}

/**
 * Configures the base android settings
 */
fun Project.androidConfig() = with(libraryExtension()) {
    namespace = NamespacePrefix + this@androidConfig.name
        .lowercase()
        .replace('-', '_')

    defaultConfig {
        compileSdk = CompileSdk
        minSdk = MinSdk

        testInstrumentationRunner = InstrumentationRunner
    }

    packaging { defaultPackaging() }
}

/**
 * Configures the base android-application settings
 */
fun Project.androidApplicationConfig() = with(applicationExtension()) {
    namespace = Namespace

    defaultConfig {
        compileSdk = CompileSdk
        minSdk = MinSdk
        targetSdk = TargetSdk

        applicationId = ApplicationId

        versionCode = VersionCode
        versionName = VersionName

        testInstrumentationRunner = InstrumentationRunner
    }

    packaging { defaultPackaging() }

    signingConfigs {
        create(SigningConfigReleaseName) {
            keyAlias = System.getenv(SigningConfigKeyAliasVarName)
            keyPassword = System.getenv(SigningConfigKeyPasswordVarName)
            storePassword = System.getenv(SigningConfigSigningStorePasswordVarName)
            storeFile = file(SigningConfigStoreFileRelativeLocation)
        }
    }

    buildTypes {
        getByName(BuildTypeReleaseName) {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName(SigningConfigReleaseName)
        }
    }
}

fun Project.kotlinConfig(isAndroid: Boolean = false) = with(kotlinExtension()) {
    jvm()

    if (isAndroid) {
        androidTarget()
    }
}