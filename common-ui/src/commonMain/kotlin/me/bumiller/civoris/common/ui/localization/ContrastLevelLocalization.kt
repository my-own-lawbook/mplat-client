package me.bumiller.civoris.common.ui.localization

import androidx.compose.runtime.Composable
import me.bumiller.civoris.common_ui.Res
import me.bumiller.civoris.common_ui.level_medium
import me.bumiller.civoris.common_ui.level_normal
import me.bumiller.civoris.model.settings.ColorSchemeContrastLevel
import org.jetbrains.compose.resources.stringResource

/**
 * Creates a localized title for the contrast level.
 *
 * @return The localized string for the contrast level
 */
@Composable
fun ColorSchemeContrastLevel.localizedName() = stringResource(
    when (this) {
        ColorSchemeContrastLevel.Normal -> Res.string.level_normal
        ColorSchemeContrastLevel.Medium -> Res.string.level_medium
        ColorSchemeContrastLevel.High -> Res.string.level_normal
    }
)