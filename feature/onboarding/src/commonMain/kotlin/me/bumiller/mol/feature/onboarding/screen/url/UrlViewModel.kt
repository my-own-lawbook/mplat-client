package me.bumiller.mol.feature.onboarding.screen.url

import me.bumiller.mol.common.ui.input.validation.validate
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.data.UserSettingsSource

/**
 * Viewmodel for the screen allowing the user to adjust the url of the backend server.
 */
class UrlViewModel(
    private val settingsSource: UserSettingsSource
) : MolViewModel<UrlUiEvent, UrlEvent>() {

    init {
        registerUiState(UrlState())
    }

    /**
     * The form state of the screen.
     */
    val formState = uiState<UrlState>()

    override suspend fun handleEvent(event: UrlUiEvent) = with(event) {
        when (this) {
            is UrlUiEvent.ChangeUrl -> handle()
            is UrlUiEvent.Confirm -> handle()
        }
    }

    private fun UrlUiEvent.ChangeUrl.handle() = updateUiState<UrlState> {
        it.copy(
            url = it.url.update(input)
        )
    }

    private fun UrlUiEvent.Confirm.handle() {
        updateUiState<UrlState> {
            it.copy(
                url = it.url.validate()
            )
        }
    }

}