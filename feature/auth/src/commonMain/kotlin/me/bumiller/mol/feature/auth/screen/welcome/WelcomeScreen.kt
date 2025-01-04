package me.bumiller.mol.feature.auth.screen.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import me.bumiller.mol.auth.Res
import me.bumiller.mol.auth.welcome_screen_description
import me.bumiller.mol.auth.welcome_screen_display
import me.bumiller.mol.auth.welcome_screen_login_button_label
import me.bumiller.mol.auth.welcome_screen_server_info
import me.bumiller.mol.auth.welcome_screen_signup_button_label
import me.bumiller.mol.auth.welcome_screen_title
import me.bumiller.mol.model.UserSettings
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.ui.components.MultiStyleText
import me.bumiller.mol.ui.components.WideButton
import me.bumiller.mol.ui.components.WideOutlinedButton
import me.bumiller.mol.ui.layout.AppBarLayoutWithDisplay
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Screen on which the user can choose their authentication method.
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun WelcomeScreen(
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {
    val viewModel = koinViewModel<WelcomeViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                WelcomeEvent.ContinueLogin -> onLogin()
                WelcomeEvent.ContinueSignup -> onSignup()
            }
        }
    }

    val settings by viewModel.settings.collectAsStateWithLifecycle()

    if (settings is SimpleState.Success<*>) {
        WelcomeScreen(viewModel::onEvent, settings.dataOrNull()!!)
    }
}

@Composable
private fun WelcomeScreen(
    onEvent: (WelcomeUiEvent) -> Unit,
    settings: UserSettings
) {
    AppBarLayoutWithDisplay(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            Text(stringResource(Res.string.welcome_screen_title))
        },
        display = {
            Text(stringResource(Res.string.welcome_screen_display))
        },
        description = {
            Text(stringResource(Res.string.welcome_screen_description))
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
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                WideButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onEvent(WelcomeUiEvent.ContinueLogin) }
                ) {
                    Text(stringResource(Res.string.welcome_screen_login_button_label))
                }

                WideOutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onEvent(WelcomeUiEvent.ContinueSignup) }
                ) {
                    Text(stringResource(Res.string.welcome_screen_signup_button_label))
                }
            }

            MultiStyleText(
                modifier = Modifier
                    .widthIn(max = 400.dp),
                style = MaterialTheme.typography.bodySmall,
                stringResource(Res.string.welcome_screen_server_info) to MaterialTheme.typography.bodySmall,
                settings.backendUrl.toString() to MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }
}