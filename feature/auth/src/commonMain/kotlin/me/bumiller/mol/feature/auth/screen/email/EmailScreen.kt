package me.bumiller.mol.feature.auth.screen.email

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.Clock
import me.bumiller.mol.auth.Res
import me.bumiller.mol.auth.cd_email_screen
import me.bumiller.mol.auth.email_screen
import me.bumiller.mol.auth.email_screen_description
import me.bumiller.mol.auth.email_screen_input_token_label
import me.bumiller.mol.auth.email_screen_resend_button_label
import me.bumiller.mol.auth.email_screen_title
import me.bumiller.mol.auth.email_screen_verify_button_label
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.ui.components.MolTextField
import me.bumiller.mol.ui.components.WideButton
import me.bumiller.mol.ui.components.WideOutlinedButton
import me.bumiller.mol.ui.layout.AppBarLayoutWithImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Composable for the email screen.
 *
 * @param onFinished The callback invoked when the email was verified.
 */
@Composable
internal fun EmailScreen(
    onFinished: () -> Unit
) {
    ViewModelScope<EmailUiEvent, EmailEvent, EmailViewModel>(
        onViewModelEvent = { event ->
            when (event) {
                EmailEvent.Finished -> onFinished()
            }
        }
    ) { vm ->
        val formState by vm.formState.collectAsStateWithLifecycle()

        EmailScreen(vm::onEvent, formState)
    }
}

@Composable
private fun EmailScreen(
    onEvent: (EmailUiEvent) -> Unit,
    formState: EmailState
) {
    AppBarLayoutWithImage(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            Text(stringResource(Res.string.email_screen_title))
        },
        imagePainter = painterResource(Res.drawable.email_screen),
        imageContentDescription = stringResource(Res.string.cd_email_screen),
        description = {
            Text(stringResource(Res.string.email_screen_description))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MolTextField(
                value = formState.token,
                onValueChange = { onEvent(EmailUiEvent.ChangeToken(it)) },
                label = {
                    Text(stringResource(Res.string.email_screen_input_token_label))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions { onEvent(EmailUiEvent.Continue) }
            )

            WideButton(
                onClick = { onEvent(EmailUiEvent.Continue) }
            ) {
                Text(stringResource(Res.string.email_screen_verify_button_label))
            }
            WideOutlinedButton(
                onClick = { onEvent(EmailUiEvent.Resend) },
                enabled = Clock.System.now() > formState.nextResendAt
            ) {
                Text(stringResource(Res.string.email_screen_resend_button_label))
            }
        }
    }
}