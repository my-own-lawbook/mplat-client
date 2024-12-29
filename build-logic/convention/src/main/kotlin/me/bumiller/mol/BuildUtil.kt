package me.bumiller.mol

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Shorthand to get a specific extension of a project
 */
inline fun <reified T : Any> Project.extension(): T {
    val n = extensions.findByType<T>()
    return n!!
}

/**
 * Gets the LibraryExtension
 */
fun Project.libraryExtension() = extension<LibraryExtension>()

/**
 * Gets the KotlinMultiplatformExtension
 */
fun Project.kotlinExtension() = extension<KotlinMultiplatformExtension>()

/**
 * Gets the VersionCatalogsExtension
 */
fun Project.versionCatalogExtension() = extension<VersionCatalogsExtension>()