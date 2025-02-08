package me.bumiller.mol.common.ui.localization

import androidx.compose.runtime.Composable
import me.bumiller.mol.common_ui.Res
import me.bumiller.mol.common_ui.gender_disclosed
import me.bumiller.mol.common_ui.gender_female
import me.bumiller.mol.common_ui.gender_male
import me.bumiller.mol.common_ui.gender_other
import me.bumiller.mol.model.user.Gender
import org.jetbrains.compose.resources.stringResource

/**
 * Creates a localized title for the gender.
 *
 * @return The localized string for the gender
 */
@Composable
fun Gender.localizedName() = stringResource(
    when (this) {
        Gender.Female -> Res.string.gender_female
        Gender.Male -> Res.string.gender_male
        Gender.NotSay -> Res.string.gender_disclosed
        Gender.Other -> Res.string.gender_other
    }
)