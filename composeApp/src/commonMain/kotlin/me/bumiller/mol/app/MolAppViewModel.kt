package me.bumiller.mol.app

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.data.UserSettingsSource
import me.bumiller.mol.model.UserSettings
import me.bumiller.mol.model.state.SimpleState

/**
 * Viewmodel that handles the top level app state, such as the user settings.
 */
class MolAppViewModel(

    /**
     * The data source for the settings
     */
    settingsSource: UserSettingsSource

) : MolViewModel<UiEvent, ViewModelEvent>() {

    /**
     * A state flow of the user settings
     */
    val settings: StateFlow<SimpleState<UserSettings>> = settingsSource.settings
        .map(SimpleState.Companion::success)
        .loadingStateIn()

    /**
     * Will wait for the first settings emission and set the initial value for the top-level-location accordingly.
     */
    init {
        viewModelScope.launch {
            val settings = settings.first {
                it.isSuccess
            }.dataOrNull()!!

            val initialLocation = if (settings.backendUrl == null) MolTopLevelLocation.Onboarding
            else MolTopLevelLocation.Home

            _topLevelLocation.emit(SimpleState.success(initialLocation))
        }
    }

    private val _topLevelLocation =
        MutableStateFlow<SimpleState<MolTopLevelLocation>>(SimpleState.loading())

    /**
     * A state flow containing the current top level location that should be displayed.
     */
    val topLevelLocation = _topLevelLocation.asStateFlow()

    override suspend fun handleEvent(event: UiEvent): Nothing =
        throw Error("No ui event should be fired.")

}