package me.bumiller.mol.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

/**
 * Method for retrieving a dynamic color scheme based on the color mode.
 *
 * @param dark Whether dark mode should be used
 * @return The color scheme, or null if dynamic is not possible
 */
@Composable
actual fun dynamicColorScheme(dark: Boolean): ColorScheme? = null