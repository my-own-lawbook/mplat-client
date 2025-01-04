package me.bumiller.mol.feature.auth.screen.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.bumiller.mol.model.UserSettings
import me.bumiller.mol.model.state.SimpleState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Screen on which the user can choose their authentication method.
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun WelcomeScreen() {
    val viewModel = koinViewModel<WelcomeViewModel>()

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

}