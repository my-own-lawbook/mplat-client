package me.bumiller.mol.feature.auth.screen.email

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.bumiller.mol.auth.AuthResult
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.common.ui.input.validation.ValidationError
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import kotlin.time.Duration.Companion.seconds

/**
 * View model for the email screen.
 */
class EmailViewModel(

    /**
     * The auth service.
     */
    private val authService: AuthService

) : MolViewModel<EmailUiEvent, EmailEvent>() {

    init {
        registerUiState(EmailState())

        viewModelScope.launch {
            requestEmailToken(false)
        }
    }

    /**
     * The form state containing input values.
     */
    val formState = uiState<EmailState>().asStateFlow()

    override suspend fun handleEvent(event: EmailUiEvent) = when (event) {
        is EmailUiEvent.ChangeToken -> updateUiState<EmailState> {
            it.copy(token = it.token.update(event.input))
        }

        is EmailUiEvent.Resend -> event.handle()
        is EmailUiEvent.Continue -> event.handle()
    }

    private suspend fun EmailUiEvent.Resend.handle() {
        requestEmailToken(true)

        val timeInDuration = formState.value.nextResendAt + 60.seconds
        updateUiState<EmailState> {
            it.copy(nextResendAt = timeInDuration)
        }
    }

    private suspend fun requestEmailToken(doFetchState: Boolean) {
        clearErrors()

        val response = if (doFetchState) {
            withFetchState {
                authService.requestEmailToken()
            }
        } else authService.requestEmailToken()
        when (response) {
            is AuthResult.NetworkError -> setHasNetworkError()
            is AuthResult.Error, is AuthResult.UnknownError -> setHasUnknownError()
            else -> {}
        }
    }

    private suspend fun EmailUiEvent.Continue.handle() {
        val token = formState.value.token.value

        val response = authService.submitEmailToken(token)

        clearErrors()

        when (response) {
            is AuthResult.Success -> fireEvent(EmailEvent.Finished)
            is AuthResult.Error -> when (response.errorType) {
                me.bumiller.mol.auth.SubmitEmailTokenError.InvalidToken -> updateUiState<EmailState> {
                    it.copy(token = it.token.copy(error = ValidationError.InvalidEmailToken))
                }
            }

            is AuthResult.NetworkError -> setHasNetworkError()
            is AuthResult.UnknownError -> setHasUnknownError()
        }
    }

}