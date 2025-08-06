package me.bumiller.civoris.common.ui.color

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

/**
 * Gets the hexadecimal string representation of the color.
 */
val Color.hexString: String
    get() {
        val red = (red * 255).roundToInt()
        val green = (green * 255).roundToInt()
        val blue = (blue * 255).roundToInt()

        return String.format("%02X%02X%02X", red, green, blue)
    }