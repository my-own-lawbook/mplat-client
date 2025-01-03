package me.bumiller.mol.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.url_textfield_prefix
import org.jetbrains.compose.resources.stringResource

/**
 * TextField that preconfigures it to ease the input of a url.
 */
@Composable
fun UrlTextField(
    value: InputValue<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextFieldStyle = TextFieldStyle.Filled,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    MolTextField(
        value,
        onValueChange,
        modifier,
        style,
        readOnly,
        textStyle,
        label,
        placeholder,
        leadingIcon,
        trailingIcon,
        prefix = {
            Text(stringResource(Res.string.url_textfield_prefix))
        },
        suffix,
        supportingText,
        visualTransformation,
        keyboardOptions = keyboardOptions.copy(
            keyboardType = KeyboardType.Uri
        ),
        keyboardActions,
        singleLine,
        maxLines,
        minLines,
        interactionSource,
        shape,
        colors
    )
}