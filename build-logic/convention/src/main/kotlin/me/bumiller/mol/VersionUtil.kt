package me.bumiller.mol

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog

private const val VersionCatalogName = "libs"

/**
 * Gets the version catalog
 */
fun Project.versionCatalog(): VersionCatalog = versionCatalogExtension().named(VersionCatalogName)

/**
 * Gets a library dependency from the version catalog
 */
fun Project.lib(notation: String) = versionCatalog().findLibrary(notation).get().get()

/**
 * Gets a plugin dependency from the version catalog
 */
fun Project.plugin(notation: String) = versionCatalog().findPlugin(notation).get().get()

/**
 * Gets a bundle dependency from the version catalog
 */
fun Project.bundle(notation: String) = versionCatalog().findBundle(notation).get().get()