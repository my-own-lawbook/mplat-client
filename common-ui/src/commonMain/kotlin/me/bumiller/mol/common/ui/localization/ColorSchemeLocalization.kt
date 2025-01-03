package me.bumiller.mol.common.ui.localization

import androidx.compose.runtime.Composable
import me.bumiller.mol.common_ui.Res
import me.bumiller.mol.common_ui.scheme_app
import me.bumiller.mol.common_ui.scheme_dynamic
import me.bumiller.mol.model.ColorScheme
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