package me.bumiller.mol

import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

enum class SourceSets {

    Common,

    Android,

    Jvm

}

/**
 * Bundles dependencies for one specific purpose, with all source sets that may need different treatments.
 */
data class DependencyBundle(

    /**
     * Dependencies for the common source set
     */
    val common: List<String>,

    /**
     * Dependencies for the android source set
     */
    val android: List<String>,

    /**
     * Dependencies for the jvm source set
     */
    val jvm: List<String>

)

/**
 * Tree of all dependencies used for specific purposes.
 */
object Dependencies {

    /**
     * Base dependencies needed for every single module.
     */
    val Base = DependencyBundle(
        common = listOf(
            "koin",
            "coroutines",
            "uri"
        ),
        android = listOf(
            "koin.android"
        ),
        jvm = emptyList()
    )

    /**
     * Dependencies used for testing.
     */
    val Test = DependencyBundle(
        common = listOf(
            "junit",
            "mockk",
            "coroutines.test"
        ),
        android = listOf(
            "androidx.test.junit",
            "androidx.test.runner",
            "coroutines.test"
        ),
        jvm = emptyList()
    )

    /**
     * Dependencies used for compose
     */
    val Compose = DependencyBundle(
        common = listOf(
            "compose.runtime",
            "compose.foundation",
            "compose.material3",
            "compose.material.icons",
            "compose.ui",
            "compose.navigation",
            "compose.animation",
            "compose.animation.graphics",
            "compose.resources",
            "koin.compose",
            "koin.compose.viewmodel",
            "lifecycle.viewmodel",
            "lifecycle.compose"
        ),
        android = listOf(
            "androidx.activity.compose",
            "koin.android.compose"
        ),
        jvm = listOf(
            "coroutines.swing"
        )
    )

}

private fun Project.dependencyBundle(
    bundle: DependencyBundle,
    sourceSets: List<SourceSets> = SourceSets.values().asList(),
    sourceSetType: String = "Main",
    commonSourceSetType: String = sourceSetType,
    androidSourceSetType: String = sourceSetType,
    jvmSourceSetType: String = sourceSetType
) {
    val mplatExt = extension<KotlinMultiplatformExtension>()

    if (SourceSets.Common in sourceSets) {
        val sourceSetCommon = mplatExt.sourceSets["common$commonSourceSetType"]
        sourceSetCommon.dependencies {
            bundle.common.map(::lib).forEach(::implementation)
        }
    }

    if (SourceSets.Android in sourceSets) {
        val sourceSetAndroid = mplatExt.sourceSets["android$androidSourceSetType"]
        sourceSetAndroid.dependencies {
            bundle.android.map(::lib).forEach(::implementation)
        }
    }

    if (SourceSets.Jvm in sourceSets) {
        val sourceSetJvm = mplatExt.sourceSets["jvm$jvmSourceSetType"]
        sourceSetJvm.dependencies {
            bundle.jvm.map(::lib).forEach(::implementation)
        }
    }
}

/**
 * Adds the base dependencies to each source set
 */
fun Project.baseDependencies(sourceSets: List<SourceSets> = SourceSets.values().asList()) {
    dependencyBundle(Dependencies.Base, sourceSets)
}

/**
 * Adds the test dependencies to each source set
 */
fun Project.testDependencies(sourceSets: List<SourceSets> = SourceSets.values().asList()) {
    dependencyBundle(
        Dependencies.Test,
        sourceSets,
        sourceSetType = "Test",
        androidSourceSetType = "InstrumentedTest"
    )
}

/**
 * Adds the compose dependencies to each source set
 */
fun Project.composeDependencies(sourceSets: List<SourceSets> = SourceSets.values().asList()) {
    dependencyBundle(Dependencies.Compose, sourceSets)
}