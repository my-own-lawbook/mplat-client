package me.bumiller.mol.common.ui.style

import androidx.compose.ui.text.TextStyle

private const val Factor = 0.39F

/**
 * Creates the disabled variant by lowering the alpha value of the font styles color.
 *
 * @return The same font style with a lowered alpha value
 */
fun TextStyle.disabledColor(): TextStyle = copy(
    color = color.copy(
        alpha = color.alpha * Factor
    )
)