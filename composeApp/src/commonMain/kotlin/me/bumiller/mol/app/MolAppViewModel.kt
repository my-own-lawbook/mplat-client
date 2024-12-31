package me.bumiller.mol.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

) : ViewModel() {

    /**
     * A state flow of the user settings
     */
    val stats: StateFlow<SimpleState<UserSettings>> = settingsSource.settings
        .map(SimpleState.Companion::success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SimpleState.Loading()
        )

}