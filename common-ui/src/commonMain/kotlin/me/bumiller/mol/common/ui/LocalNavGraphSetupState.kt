package me.bumiller.mol.common.ui

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Contains whether the nav graph has already been set up before, or not.
 */
val LocalNavGraphSetupState = staticCompositionLocalOf { false }