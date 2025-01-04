package me.bumiller.mol.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

/**
 * Utility component to color a text in multiple colors.
 *
 * @param modifier The modifier
 * @param style The text style
 * @param pairs The pairs, assigning each text a specific color.
 */
@Composable
fun MulticolorText(
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    vararg pairs: Pair<String, Color>
) {
    Text(
        modifier = modifier,
        style = style,
        text = buildAnnotatedString {
            pairs.forEach { (text, color) ->
                withStyle(SpanStyle(color)) {
                    append(text)
                }
            }
        }
    )
}