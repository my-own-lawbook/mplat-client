package me.bumiller.mol.feature.onboarding.screen.url

import com.eygraber.uri.Url
import kotlinx.coroutines.flow.first
import me.bumiller.mol.common.ui.input.validation.ValidationError
import me.bumiller.mol.common.ui.input.validation.validate
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.data.UserSettingsSource
import me.bumiller.mol.network.ServerStatusChecker

/**
 * Viewmodel for the screen allowing the user to adjust the url of the backend server.
 */
class UrlViewModel(
    private val settingsSource: UserSettingsSource,
    private val serverStatusChecker: ServerStatusChecker
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

    private suspend fun UrlUiEvent.Confirm.handle() {
        updateUiState<UrlState> {
            it.copy(
                url = it.url.validate()
            )
        }

        if (formState.value.url.isError()) return

        val urlWithScheme = "https://${formState.value.url.value}"

        val canReachServer = withFetchState {
            serverStatusChecker.checkServerConnection(urlWithScheme)
        }

        if (!canReachServer) {
            updateUiState<UrlState> {
                it.copy(
                    url = it.url.copy(error = ValidationError.CantReachUrl)
                )
            }

            return
        }

        val url = Url.parse(urlWithScheme)

        val settings = settingsSource.settings.first()
        settingsSource.update(settings.copy(backendUrl = url))

        fireEvent(UrlEvent.Continue)
    }

}