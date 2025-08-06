package me.bumiller.civoris.common.ui.localization

import androidx.compose.runtime.Composable
import me.bumiller.civoris.common_ui.Res
import me.bumiller.civoris.common_ui.scheme_app
import me.bumiller.civoris.common_ui.scheme_dynamic
import me.bumiller.civoris.model.settings.ColorScheme
import org.jetbrains.compose.resources.stringResource

/**
 * Creates a localized title for the color scheme.
 *
 * @return The localized string for the color scheme
 */
@Composable
fun ColorScheme.localizedName() = stringResource(
    when (this) {
        ColorScheme.App -> Res.string.scheme_app
        ColorScheme.Dynamic -> Res.string.scheme_dynamic
    }
)