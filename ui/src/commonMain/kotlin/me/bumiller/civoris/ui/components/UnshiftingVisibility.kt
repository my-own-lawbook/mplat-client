package me.bumiller.civoris.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

/**
 * Composable which makes an element visible when [visible] is true, but does not remove it from the composition.
 *
 * @param visible Whether [content] is visible
 * @param modifier The modifier to apply
 * @param content The content to be controller
 */
@Composable
fun UnshiftingVisibility(
    visible: Boolean = true,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .alpha(if (visible) 1F else 0F),
        content = content
    )
}