package me.bumiller.mol.feature.auth.screen.signup

import kotlinx.coroutines.flow.asStateFlow
import me.bumiller.mol.auth.AuthResult
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.common.ui.input.validation.ValidationError
import me.bumiller.mol.common.ui.input.validation.validate
import me.bumiller.mol.feature.auth.model.SignupStage
import me.bumiller.mol.feature.auth.screen.SignupStageViewmodel

/**
 * The view model for the signup screen.
 */
internal class SignupViewModel(

    /**
     * The auth service.
     */
    private val authService: AuthService

) : SignupStageViewmodel<SignupUiEvent>(SignupStage.NotStarted, authService) {

    init {
        registerUiState(SignupState())
    }

    /**
     * State containing the form values.
     */
    val formState = uiState<SignupState>().asStateFlow()

    override suspend fun handleEvent(event: SignupUiEvent) = with(event) {
        when (this) {
            is SignupUiEvent.ChangeEmail -> updateUiState<SignupState> {
                it.copy(email = it.email.update(input))
            }

            is SignupUiEvent.ChangeUsername -> updateUiState<SignupState> {
                it.copy(username = it.username.update(input))
            }

            is SignupUiEvent.ChangePassword -> updateUiState<SignupState> {
                it.copy(password = it.password.update(input))
            }

            is SignupUiEvent.ChangePasswordConfirmation -> updateUiState<SignupState> {
                it.copy(passwordConfirmation = it.passwordConfirmation.update(input))
            }

            SignupUiEvent.Back -> fireEvent(SignupEvent.Back)
            SignupUiEvent.Login -> fireEvent(SignupEvent.Login)
            is SignupUiEvent.Confirm -> handle()
        }
    }

    private suspend fun SignupUiEvent.Confirm.handle() {
        updateUiState<SignupState> {
            it.copy(
                email = it.email.validate(),
                password = it.password.validate(),
                username = it.username.validate(),
                passwordConfirmation = it.passwordConfirmation.copy(
                    error = if (it.passwordConfirmation.value == it.password.value) null
                    else ValidationError.PasswordConfirm
                )
            )
        }

        val isError =
            formState.value.run { email.isError() || password.isError() || passwordConfirmation.isError() || username.isError() }
        if (isError) return

        val (email, username, password) = formState.value.run {
            listOf(
                email.value,
                username.value,
                password.value
            )
        }

        val signupResponse = withFetchState {
            authService.createUser(email, username, password).apply {
                if (success) {
                    authService.login(email, password)
                }
            }
        }

        when (signupResponse) {
            is AuthResult.Success -> requestStageCheck()

            is AuthResult.Error -> when (signupResponse.errorType) {
                me.bumiller.mol.auth.SignupError.EmailNotUnique -> updateUiState<SignupState> {
                    it.copy(email = it.email.copy(error = ValidationError.EmailTaken))
                }

                me.bumiller.mol.auth.SignupError.UsernameNotUnique -> updateUiState<SignupState> {
                    it.copy(username = it.username.copy(error = ValidationError.UsernameTaken))
                }
            }

            is AuthResult.NetworkError -> setHasNetworkError()
            is AuthResult.UnknownError -> setHasUnknownError()
        }
    }

}