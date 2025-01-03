package me.bumiller.mol

import com.android.build.api.dsl.Packaging
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.resources.ResourcesExtension

private fun Packaging.defaultPackaging() {
    resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
        merges += "META-INF/LICENSE.md"
        merges += "META-INF/LICENSE-notice.md"
    }
}

private fun Project.getNamespace() = NamespacePrefix + this.name
    .lowercase()
    .replace('-', '_')

/**
 * Configures the base android settings
 */
fun Project.androidConfig() = with(libraryExtension()) {
    namespace = getNamespace()

    defaultConfig {
        compileSdk = CompileSdk
        minSdk = MinSdk

        testInstrumentationRunner = InstrumentationRunner
    }

    packaging { defaultPackaging() }
}

/**
 * Configures the base compose settings
 */
fun Project.composeConfig() {

    // Workaround according to https://github.com/JetBrains/compose-multiplatform/issues/4711
    configurations.all {
        resolutionStrategy {
            force("androidx.compose.material:material-ripple:1.7.0-alpha01")
        }
    }

    with(composeExtension()) {
        with(this.extensions.getByType<ResourcesExtension>()) {
            publicResClass = false
            packageOfResClass = getNamespace()
            generateResClass = always
        }
    }
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