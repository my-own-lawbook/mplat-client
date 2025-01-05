package me.bumiller.mol.app

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.model.UserSettings
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.settings.UserSettingsSource

/**
 * Viewmodel that handles the top level app state, such as the user settings.
 */
class MolAppViewModel(

    /**
     * The data source for the settings
     */
    settingsSource: UserSettingsSource,

    /**
     * The http api for authentication
     */
    authApi: AuthService

) : MolViewModel<UiEvent, ViewModelEvent>() {

    /**
     * A state flow of the user settings
     */
    val settings: StateFlow<SimpleState<UserSettings>> = settingsSource.settings
        .map(SimpleState.Companion::success)
        .loadingStateIn()

    init {
        registerUiState<SimpleState<MolTopLevelLocation>>(SimpleState.loading())
    }

    /**
     * A state flow containing the initial top level location.
     */
    val topLevelLocation = uiState<SimpleState<MolTopLevelLocation>>()

    /**
     * Will wait for the first settings emission and set the initial value for the top-level-location accordingly.
     */
    init {
        viewModelScope.launch {
            val settings = settings.first {
                it.isSuccess
            }.dataOrNull()!!

            val refreshResponse = authApi.login()

            val initialLocation =
                if (settings.backendUrl == null) MolTopLevelLocation.Onboarding(true)
            else if (!refreshResponse.success) MolTopLevelLocation.Auth
            else MolTopLevelLocation.Home

            updateUiState<SimpleState<MolTopLevelLocation>> { SimpleState.success(initialLocation) }
        }
    }

    override suspend fun handleEvent(event: UiEvent): Nothing =
        throw Error("No ui event should be fired.")

}