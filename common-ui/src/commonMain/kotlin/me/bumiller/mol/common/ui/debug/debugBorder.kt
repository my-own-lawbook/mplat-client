package me.bumiller.mol.common.ui.debug

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.random.Random

/**
 * Adds a border around the component to observe component sizes.
 */
fun Modifier.debugBorder() = this then border(
    width = Dp.Hairline,
    color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
)