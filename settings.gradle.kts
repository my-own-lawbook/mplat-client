rootProject.name = "mplat-client"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
    }
}

include(":composeApp")
include(":model")
include(":data")
include(":ui")
include(":feature:onboarding")
include(":feature:about")
include(":feature:home")
include(":feature:profile")
include(":feature:dashboard")
include(":database")
include(":feature:auth")
include(":sync")
include(":common-ui")
include(":auth")
include(":network")
include(":settings")
include(":domain")