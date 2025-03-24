package me.bumiller.mol.feature.auth.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private const val DIGITS_COUNT = 6

/**
 * Composable for the OTP-input views component.
 *
 * @param modifier The composable modifier
 * @param onChange The callback invoked when the whole text has been filled
 */
@Composable
fun OtpTextViews(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean,
    onChange: (String, Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = value,
        onValueChange = {
            if (it.length < value.length || it.length <= DIGITS_COUNT) {
                val finished = it.length == DIGITS_COUNT
                onChange(it, finished)
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        decorationBox = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                repeat(DIGITS_COUNT) { index ->
                    DigitInput(
                        modifier = Modifier,
                        isError = isError,
                        digit = value.getOrNull(index),
                        isActive = index == value.length || (value.length == DIGITS_COUNT && index == DIGITS_COUNT - 1)
                    )
                }
            }
        }
    )
}

@Composable
private fun DigitInput(
    modifier: Modifier = Modifier,
    isError: Boolean,
    digit: Char?,
    isActive: Boolean
) {
    val containerColor = MaterialTheme.colorScheme.run {
        when (isActive) {
            true -> when (isError) {
                true -> MaterialTheme.colorScheme.errorContainer
                false -> MaterialTheme.colorScheme.primary
            }

            false -> when (isError) {
                true -> MaterialTheme.colorScheme.errorContainer
                false -> MaterialTheme.colorScheme.secondary
            }
        }
    }
    val shape = RoundedCornerShape(8.dp)

    Text(
        modifier = modifier
            .width(30.dp)
            .border(width = 3.dp, color = containerColor, shape = shape)
            .padding(vertical = 8.dp, horizontal = 10.dp),
        text = digit?.toString() ?: "",
        style = MaterialTheme.typography.titleLarge.copy(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    )
}