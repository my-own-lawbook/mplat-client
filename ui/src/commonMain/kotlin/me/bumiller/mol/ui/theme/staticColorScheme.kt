package me.bumiller.mol.ui.theme

import me.bumiller.mol.model.ColorSchemeContrastLevel

/**
 * Method for retrieving the static color scheme based on settings.
 *
 * @param darkMode Whether a dark color scheme should be used
 * @param contrastLevel The contrast level that should be used
 * @return The color scheme
 */
fun staticColorScheme(darkMode: Boolean = false, contrastLevel: ColorSchemeContrastLevel) =
    when (contrastLevel) {
        ColorSchemeContrastLevel.Normal -> if (darkMode) darkScheme else lightScheme
        ColorSchemeContrastLevel.Medium -> if (darkMode) mediumContrastDarkColorScheme else mediumContrastLightColorScheme
        ColorSchemeContrastLevel.High -> if (darkMode) highContrastDarkColorScheme else highContrastLightColorScheme
    }