package me.bumiller.mol.feature.auth.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.bumiller.mol.auth.Res
import me.bumiller.mol.auth.login_screen_description
import me.bumiller.mol.auth.login_screen_display
import me.bumiller.mol.auth.login_screen_input_email_label
import me.bumiller.mol.auth.login_screen_input_password_label
import me.bumiller.mol.auth.login_screen_login_button_label
import me.bumiller.mol.auth.login_screen_signup_link
import me.bumiller.mol.auth.login_screen_signup_link_prefix
import me.bumiller.mol.auth.login_screen_signup_link_suffix
import me.bumiller.mol.auth.login_screen_title
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.ui.components.BackIconButton
import me.bumiller.mol.ui.components.MolTextField
import me.bumiller.mol.ui.components.MultiStyleText
import me.bumiller.mol.ui.components.PasswordTextField
import me.bumiller.mol.ui.components.TextFieldStyle
import me.bumiller.mol.ui.components.WideButton
import me.bumiller.mol.ui.layout.AppBarLayoutWithDisplay
import org.jetbrains.compose.resources.stringResource

/**
 * Composable for the login screen, where a user authenticates by entering the credentials.
 *
 * @param onBack The callback invoked when the back button is clicked.
 * @param onSignup The callback invoked when the link to the signup screen is clicked.
 * @param onAuthenticate The callback invoked when the user successfully authenticated.
 */
@Composable
internal fun LoginScreen(
    onBack: () -> Unit,
    onSignup: () -> Unit,
    onAuthenticate: () -> Unit
) {
    ViewModelScope<LoginUiEvent, LoginEvent, LoginViewModel>(
        onViewModelEvent = { event ->
            when (event) {
                LoginEvent.Back -> onBack()
                LoginEvent.LoggedIn -> onAuthenticate()
                LoginEvent.Signup -> onSignup()
            }
        }
    ) { vm ->
        val formState by vm.formState.collectAsStateWithLifecycle()

        LoginScreen(vm::onEvent, formState)
    }
}

@Composable
private fun LoginScreen(
    onEvent: (LoginUiEvent) -> Unit,
    formState: LoginState
) {
    AppBarLayoutWithDisplay(
        modifier = Modifier
            .fillMaxSize(),
        navigationIcon = {
            BackIconButton {
                onEvent(LoginUiEvent.Back)
            }
        },
        title = {
            Text(stringResource(Res.string.login_screen_title))
        },
        display = {
            Text(stringResource(Res.string.login_screen_display))
        },
        description = {
            Text(stringResource(Res.string.login_screen_description))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier)

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MolTextField(
                    value = formState.email,
                    onValueChange = { onEvent(LoginUiEvent.ChangeEmail(it)) },
                    style = TextFieldStyle.Outlined,
                    label = { Text(stringResource(Res.string.login_screen_input_email_label)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                PasswordTextField(
                    value = formState.password,
                    onValueChange = { onEvent(LoginUiEvent.ChangePassword(it)) },
                    style = TextFieldStyle.Outlined,
                    label = { Text(stringResource(Res.string.login_screen_input_password_label)) }
                )

                WideButton(
                    onClick = { onEvent(LoginUiEvent.Confirm) }
                ) {
                    Text(stringResource(Res.string.login_screen_login_button_label))
                }
            }

            MultiStyleText(
                modifier = Modifier
                    .widthIn(max = 400.dp),
                style = MaterialTheme.typography.bodySmall,
                stringResource(Res.string.login_screen_signup_link_prefix) to MaterialTheme.typography.bodySmall,
                stringResource(Res.string.login_screen_signup_link) to MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
                stringResource(Res.string.login_screen_signup_link_suffix) to MaterialTheme.typography.bodySmall,
            )
        }
    }
}