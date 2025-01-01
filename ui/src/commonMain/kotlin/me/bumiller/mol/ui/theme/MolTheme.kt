package me.bumiller.mol.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import me.bumiller.mol.model.ColorMode
import me.bumiller.mol.model.ColorScheme
import me.bumiller.mol.model.ColorSchemeContrastLevel

/**
 * Wrapper around the material-3 theme for configuring the theme for this app.
 *
 * @param darkTheme Whether to use a dark color palette
 * @param dynamicTheme Whether to try and use a dynamic color palette. No guarantee, depending on version and platform.
 * @param content The content
 */
@Composable
fun MolTheme(
    colorMode: ColorMode = ColorMode.System,
    colorScheme: ColorScheme = ColorScheme.App,
    contrastLevel: ColorSchemeContrastLevel = ColorSchemeContrastLevel.Normal,
    content: @Composable () -> Unit
) {
    val isDarkMode = when (colorMode) {
        ColorMode.Dark -> true
        ColorMode.Light -> false
        ColorMode.System -> isSystemInDarkTheme()
    }
    val dynamicColorScheme = dynamicColorScheme(isDarkMode)
    val staticColorScheme = staticColorScheme(isDarkMode, contrastLevel)

    val toUseColorScheme = when (colorScheme) {
        ColorScheme.App -> staticColorScheme
        ColorScheme.Dynamic -> dynamicColorScheme ?: staticColorScheme
    }

    MaterialTheme(
        colorScheme = toUseColorScheme,
        content = content
    )
}