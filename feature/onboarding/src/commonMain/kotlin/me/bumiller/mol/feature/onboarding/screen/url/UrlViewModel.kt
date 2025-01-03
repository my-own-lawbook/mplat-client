package me.bumiller.mol.feature.onboarding.screen.url

import androidx.lifecycle.viewModelScope
import com.eygraber.uri.Url
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.bumiller.mol.common.ui.input.validation.ValidationError
import me.bumiller.mol.common.ui.input.validation.validate
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.network.ServerStatusChecker
import me.bumiller.mol.settings.UserSettingsSource

/**
 * Viewmodel for the screen allowing the user to adjust the url of the backend server.
 */
class UrlViewModel(
    private val settingsSource: UserSettingsSource,
    private val serverStatusChecker: ServerStatusChecker
) : MolViewModel<UrlUiEvent, UrlEvent>() {

    init {
        registerUiState(UrlState())

        viewModelScope.launch {
            val settings = settingsSource.settings.first()
            val url = settings.backendUrl?.toString()?.removePrefix("https://")

            url?.let {
                updateUiState<UrlState> {
                    it.copy(
                        url = it.url.update(url)
                    )
                }
            }
        }
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