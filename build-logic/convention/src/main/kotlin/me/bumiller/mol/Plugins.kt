package me.bumiller.mol

import org.gradle.api.Project
import org.gradle.plugin.use.PluginDependency

private fun Project.applyPlugin(plugin: PluginDependency) = pluginManager.apply(plugin.pluginId)

const val MultiplatformPluginNotation = "kotlinMultiplatform"
const val AndroidLibraryPluginNotation = "androidLibrary"

/**
 * Applies the multiplatform plugin
 */
fun Project.multiplatformPlugin() = with(versionCatalog()) {
    applyPlugin(plugin(MultiplatformPluginNotation))
}

/**
 * Applies the android library plugin
 */
fun Project.androidLibraryPlugin() = with(versionCatalog()) {
    applyPlugin(plugin(AndroidLibraryPluginNotation))
}