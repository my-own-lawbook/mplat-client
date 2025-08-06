package me.bumiller.mol.feature.auth.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.bumiller.civoris.auth.Res
import me.bumiller.civoris.auth.signup_screen_description
import me.bumiller.civoris.auth.signup_screen_display
import me.bumiller.civoris.auth.signup_screen_input_email_label
import me.bumiller.civoris.auth.signup_screen_input_password_confirm_label
import me.bumiller.civoris.auth.signup_screen_input_password_label
import me.bumiller.civoris.auth.signup_screen_input_username_label
import me.bumiller.civoris.auth.signup_screen_login_link
import me.bumiller.civoris.auth.signup_screen_login_link_prefix
import me.bumiller.civoris.auth.signup_screen_login_link_suffix
import me.bumiller.civoris.auth.signup_screen_signup_button_label
import me.bumiller.civoris.auth.signup_screen_title
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.feature.auth.model.SignupStage
import me.bumiller.mol.feature.auth.screen.SignupStageEvent
import me.bumiller.mol.ui.components.BackIconButton
import me.bumiller.mol.ui.components.CivorisTextField
import me.bumiller.mol.ui.components.MultiStyleText
import me.bumiller.mol.ui.components.PasswordTextField
import me.bumiller.mol.ui.components.TextFieldStyle
import me.bumiller.mol.ui.components.TextStyleWithCallback
import me.bumiller.mol.ui.components.WideButton
import me.bumiller.mol.ui.layout.AppBarLayoutWithDisplay
import org.jetbrains.compose.resources.stringResource

/**
 * Composable for the signup screen.
 *
 * @param onBack The callback for when the user wants to return to the recent screen
 * @param onStageChange The callback for when the signup stage changes
 * @param onLogin The callback for when the user navigates to the login screen
 */
@Composable
internal fun SignupScreen(
    onBack: () -> Unit,
    onStageChange: (SignupStage) -> Unit,
    onLogin: () -> Unit
) {
    ViewModelScope<SignupUiEvent, SignupStageEvent, SignupViewModel>(
        onViewModelEvent = { event ->
            when (event) {
                is SignupStageEvent.SignupStageChanged -> onStageChange(event.stage)
                SignupEvent.Login -> onLogin()
                SignupEvent.Back -> onBack()
            }
        }
    ) { vm ->
        val formState by vm.formState.collectAsStateWithLifecycle()

        SignupScreen(vm::onEvent, formState)
    }
}

@Composable
private fun SignupScreen(
    onEvent: (SignupUiEvent) -> Unit,
    formState: SignupState
) {
    AppBarLayoutWithDisplay(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            Text(stringResource(Res.string.signup_screen_title))
        },
        display = {
            Text(stringResource(Res.string.signup_screen_display))
        },
        description = {
            Text(stringResource(Res.string.signup_screen_description))
        },
        navigationIcon = {
            BackIconButton {
                onEvent(SignupUiEvent.Back)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier)

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CivorisTextField(
                    value = formState.email,
                    onValueChange = { onEvent(SignupUiEvent.ChangeEmail(it)) },
                    style = TextFieldStyle.Outlined,
                    label = { Text(stringResource(Res.string.signup_screen_input_email_label)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                CivorisTextField(
                    value = formState.username,
                    onValueChange = { onEvent(SignupUiEvent.ChangeUsername(it)) },
                    style = TextFieldStyle.Outlined,
                    label = { Text(stringResource(Res.string.signup_screen_input_username_label)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                PasswordTextField(
                    value = formState.password,
                    onValueChange = { onEvent(SignupUiEvent.ChangePassword(it)) },
                    style = TextFieldStyle.Outlined,
                    label = { Text(stringResource(Res.string.signup_screen_input_password_label)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                PasswordTextField(
                    value = formState.passwordConfirmation,
                    onValueChange = { onEvent(SignupUiEvent.ChangePasswordConfirmation(it)) },
                    style = TextFieldStyle.Outlined,
                    label = { Text(stringResource(Res.string.signup_screen_input_password_confirm_label)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions { onEvent(SignupUiEvent.Confirm) }
                )

                WideButton(
                    onClick = { onEvent(SignupUiEvent.Confirm) }
                ) {
                    Text(stringResource(Res.string.signup_screen_signup_button_label))
                }
            }

            MultiStyleText(
                modifier = Modifier
                    .widthIn(max = 400.dp),
                style = MaterialTheme.typography.bodySmall,
                stringResource(Res.string.signup_screen_login_link_prefix) to TextStyleWithCallback(
                    MaterialTheme.typography.bodySmall
                ),
                stringResource(Res.string.signup_screen_login_link) to TextStyleWithCallback(
                    style = MaterialTheme.typography.bodySmall
                        .copy(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ),
                    callback = { onEvent(SignupUiEvent.Login) }
                ),
                stringResource(Res.string.signup_screen_login_link_suffix) to TextStyleWithCallback(
                    MaterialTheme.typography.bodySmall
                ),
            )
        }
    }
}