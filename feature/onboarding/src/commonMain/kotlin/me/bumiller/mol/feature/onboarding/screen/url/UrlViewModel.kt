package me.bumiller.mol.feature.onboarding.screen.url

import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.data.UserSettingsSource

/**
 * Viewmodel for the screen allowing the user to adjust the url of the backend server.
 */
class UrlViewModel(
    private val settingsSource: UserSettingsSource
) : MolViewModel() {

    init {
        registerUiState(0)
    }

    val counterState = uiState<Int>()

    fun incCounter() = updateUiState<Int> { it + 1 }

}