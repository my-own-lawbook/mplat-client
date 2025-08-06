package me.bumiller.mol.common.ui.localization

import androidx.compose.runtime.Composable
import me.bumiller.civoris.common_ui.Res
import me.bumiller.civoris.common_ui.mode_dark
import me.bumiller.civoris.common_ui.mode_light
import me.bumiller.civoris.common_ui.mode_system
import me.bumiller.mol.model.settings.ColorMode
import org.jetbrains.compose.resources.stringResource

/**
 * Creates a localized title for the color mode.
 *
 * @return The localized string for the color mode
 */
@Composable
fun ColorMode.localizedName() = stringResource(
    when (this) {
        ColorMode.Dark -> Res.string.mode_dark
        ColorMode.Light -> Res.string.mode_light
        ColorMode.System -> Res.string.mode_system
    }
)