package me.bumiller.mol.common.ui.style

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

private const val DisabledFactor = 0.39F

/**
 * Creates the disabled variant by lowering the alpha value of the font styles color.
 *
 * @return The same font style with a lowered alpha value
 */
@Composable
fun TextStyle.disabledColor(): TextStyle = copy(
    color = LocalContentColor.current.disabledColor()
)

private fun Color.disabledColor() = copy(alpha = alpha * DisabledFactor)