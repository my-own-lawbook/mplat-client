package me.bumiller.mol.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Method for retrieving a dynamic color scheme based on the color mode.
 *
 * @param dark Whether dark mode should be used
 * @return The color scheme, or null if dynamic is not possible
 */
@Composable
actual fun dynamicColorScheme(dark: Boolean): ColorScheme? =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) null
    else if (dark) dynamicDarkColorScheme(LocalContext.current)
    else dynamicLightColorScheme(LocalContext.current)