package me.bumiller.mol

import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

private fun Project.dependencies(
    sourceSetName: String,
    configure: KotlinDependencyHandler.() -> Unit
) = extension<KotlinMultiplatformExtension>().sourceSets[sourceSetName].dependencies(configure)

/**
 * Adds the normal dependencies to the 'commonMain' source set
 */
fun Project.dependencies() = dependencies("commonMain")

/**
 * Adds the normal Unit test dependencies to the 'commonTest' source set
 */
fun Project.testDependencies() = testDependencies("commonTest")

/**
 * Adds the android dependencies to the 'androidMain' source set
 */
fun Project.androidDependencies() = androidDependencies("androidMain")

/**
 * Adds the android instrumentation test dependencies to the 'androidInstrumentedTest' source set
 */
fun Project.androidInstrumentedTestDependencies() =
    androidInstrumentedTestDependencies("androidInstrumentedTest")


private fun Project.dependencies(sourceSetName: String) = dependencies(sourceSetName) {
    implementation(lib("koin"))
    implementation(lib("coroutines"))
    implementation(lib("google.guava.workaround"))
}

private fun Project.testDependencies(sourceSetName: String) = dependencies(sourceSetName) {
    implementation(lib("junit"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
}

private fun Project.androidDependencies(sourceSetName: String) = dependencies(sourceSetName) {
    dependencies(sourceSetName)
    implementation(lib("google.guava.workaround"))
}

private fun Project.androidInstrumentedTestDependencies(sourceSetName: String) =
    dependencies(sourceSetName) {
        testDependencies(sourceSetName)
        implementation(lib("androidx.test.junit"))
        implementation(lib("androidx.test.runner"))
    }