package me.bumiller.civoris.feature.auth.screen.welcome

import kotlinx.coroutines.flow.map
import me.bumiller.civoris.common.ui.viewmodel.CivorisViewModel
import me.bumiller.civoris.model.state.SimpleState
import me.bumiller.civoris.settings.UserSettingsSource

/**
 * View model for the [WelcomeScreen].
 */
class WelcomeViewModel(

    /**
     * The settings source.
     */
    settingsSource: UserSettingsSource

) : CivorisViewModel<WelcomeUiEvent, WelcomeEvent>() {

    /**
     * Contains the users settings
     */
    val settings = settingsSource.settings
        .map(SimpleState.Companion::success)
        .loadingStateIn()

    override suspend fun handleEvent(event: WelcomeUiEvent) = when (event) {
        WelcomeUiEvent.ContinueLogin -> fireEvent(WelcomeEvent.ContinueLogin)
        WelcomeUiEvent.ContinueSignup -> fireEvent(WelcomeEvent.ContinueSignup)
        WelcomeUiEvent.ChangeUrl -> fireEvent(WelcomeEvent.ChangeUrl)
    }

}