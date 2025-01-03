package me.bumiller.mol.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import me.bumiller.mol.model.ColorMode
import me.bumiller.mol.model.ColorScheme
import me.bumiller.mol.model.ColorSchemeContrastLevel
import me.bumiller.mol.ui.locals.LocalWindowSizeClass

/**
 * Wrapper around the material-3 theme for configuring the theme for this app.
 *
 * @param colorMode The color mode to use
 * @param colorScheme The color scheme to use. No guarantee for dynamic colors, depending on version and platform
 * @param contrastLevel The contrast level to use
 * @param content The content
 */
@Composable
fun MolTheme(
    windowSizeClass: WindowSizeClass,
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

    CompositionLocalProvider(
        LocalWindowSizeClass provides windowSizeClass
    ) {
        MaterialTheme(
            colorScheme = toUseColorScheme,
            content = content
        )
    }
}