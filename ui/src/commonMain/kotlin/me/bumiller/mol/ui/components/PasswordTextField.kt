package me.bumiller.mol.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.cd_password_textfield_invisible
import me.bumiller.mol.ui.cd_password_textfield_visible
import org.jetbrains.compose.resources.stringResource

/**
 * Text field which predefines the formatting used for password, e.g. a button to toggle the visibility.
 */
@Composable
fun PasswordTextField(
    value: InputValue<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextFieldStyle = TextFieldStyle.Filled,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    var passwordShown by remember { mutableStateOf(false) }

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
        trailingIcon = {
            IconButton(
                onClick = { passwordShown = !passwordShown }
            ) {
                Icon(
                    imageVector = if (passwordShown) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = stringResource(
                        resource = if (passwordShown) Res.string.cd_password_textfield_visible
                        else Res.string.cd_password_textfield_invisible
                    )
                )
            }
        },
        prefix,
        suffix,
        supportingText,
        visualTransformation = if (passwordShown) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        keyboardActions,
        singleLine = true,
        maxLines = 1,
        minLines,
        interactionSource,
        shape,
        colors
    )
}