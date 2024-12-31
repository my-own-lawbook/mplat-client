package me.bumiller.mol

const val ApplicationId = "me.bumiller.mol"
const val Namespace = ApplicationId
const val NamespacePrefix = "$Namespace."

const val CompileSdk = 34
const val TargetSdk = CompileSdk
const val MinSdk = 24

const val VersionCode = 2
const val VersionName = "0.0.2"

const val SigningConfigReleaseName = "release"
const val SigningConfigKeyAliasVarName = "SIGNING_KEY_ALIAS"
const val SigningConfigKeyPasswordVarName = "SIGNING_KEY_PASSWORD"
const val SigningConfigSigningStorePasswordVarName = "SIGNING_STORE_PASSWORD"
const val SigningConfigStoreFileRelativeLocation = "./../release-keystore.jks"

const val BuildTypeReleaseName = "release"

const val InstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"