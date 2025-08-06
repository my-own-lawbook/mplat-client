package me.bumiller.civoris.common.ui.operator

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection

/**
 * Adds two [PaddingValues] by components.
 *
 * @param other The other padding values
 * @return The vector sum of the padding values
 */
@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val ldr = LocalLayoutDirection.current

    return PaddingValues(
        start = this.calculateStartPadding(ldr) + other.calculateStartPadding(ldr),
        top = this.calculateTopPadding() + other.calculateTopPadding(),
        end = this.calculateEndPadding(ldr) + other.calculateEndPadding(ldr),
        bottom = this.calculateBottomPadding() + other.calculateBottomPadding()
    )
}