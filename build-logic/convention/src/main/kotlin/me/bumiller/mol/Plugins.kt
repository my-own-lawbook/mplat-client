package me.bumiller.mol

import org.gradle.api.Project
import org.gradle.plugin.use.PluginDependency

fun Project.applyPlugin(plugin: PluginDependency) = pluginManager.apply(plugin.pluginId)
fun Project.applyPlugin(pluginId: String) = pluginManager.apply(pluginId)

const val MultiplatformPluginNotation = "kotlinMultiplatform"

const val ComposeJetbrainsPluginNotation = "jetbrainsCompose"
const val ComposeCompilerPluginNotation = "compose.compiler"

const val SerializationPluginNotation = "serialization"

const val AndroidLibraryPluginNotation = "androidLibrary"
const val AndroidApplicationPluginNotation = "androidApplication"

/**
 * Applies the necessary compose plugins
 */
fun Project.composePlugins() {
    applyPlugin(plugin(ComposeJetbrainsPluginNotation))
    applyPlugin(plugin(ComposeCompilerPluginNotation))
}

/**
 * Applies the multiplatform plugin
 */
fun Project.multiplatformPlugin() {
    applyPlugin(plugin(MultiplatformPluginNotation))
}

/**
 * Applies the kotlin serialization plugin
 */
fun Project.serializationPlugin() {
    applyPlugin(plugin(SerializationPluginNotation))
}

/**
 * Applies the android library/application plugin
 */
fun Project.androidPlugin(application: Boolean = false) {
    applyPlugin(
        plugin(
            if (application) AndroidApplicationPluginNotation
            else AndroidLibraryPluginNotation
        )
    )
}