package me.bumiller.civoris.app

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.bumiller.civoris.auth.AuthService
import me.bumiller.civoris.common.ui.event.UiEvent
import me.bumiller.civoris.common.ui.event.ViewModelEvent
import me.bumiller.civoris.common.ui.nav.CivorisTopLevelLocation
import me.bumiller.civoris.common.ui.viewmodel.CivorisViewModel
import me.bumiller.civoris.model.settings.UserSettings
import me.bumiller.civoris.model.state.SimpleState
import me.bumiller.civoris.settings.UserSettingsSource

/**
 * Viewmodel that handles the top level app state, such as the user settings.
 */
class CivorisAppViewModel(

    /**
     * The data source for the settings
     */
    settingsSource: UserSettingsSource,

    /**
     * The http api for authentication
     */
    authApi: AuthService

) : CivorisViewModel<UiEvent, ViewModelEvent>() {

    /**
     * A state flow of the user settings
     */
    val settings: StateFlow<SimpleState<UserSettings>> = settingsSource.settings
        .map(SimpleState.Companion::success)
        .loadingStateIn()

    init {
        registerUiState<SimpleState<CivorisTopLevelLocation>>(SimpleState.loading())
    }

    /**
     * A state flow containing the initial top level location.
     */
    val topLevelLocation = uiState<SimpleState<CivorisTopLevelLocation>>().asStateFlow()

    /**
     * Will wait for the first settings emission and set the initial value for the top-level-location accordingly.
     */
    init {
        viewModelScope.launch {
            val settings = settings.first {
                it.isSuccess
            }.dataOrNull()!!

            val profileResponse = authApi.getProfile()

            val initialLocation =
                if (settings.backendUrl == null) CivorisTopLevelLocation.Onboarding(true)
                else if (!profileResponse.success) CivorisTopLevelLocation.Auth
            else CivorisTopLevelLocation.Home

            updateUiState<SimpleState<CivorisTopLevelLocation>> { SimpleState.success(initialLocation) }
        }
    }

    override suspend fun handleEvent(event: UiEvent): Nothing =
        throw Error("No ui event should be fired.")

}