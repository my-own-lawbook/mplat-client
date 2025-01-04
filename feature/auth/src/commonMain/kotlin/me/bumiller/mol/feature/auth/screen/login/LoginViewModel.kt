package me.bumiller.mol.feature.auth.screen.login

import me.bumiller.mol.auth.AuthResult
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.auth.LoginError
import me.bumiller.mol.common.ui.input.validation.ValidationError
import me.bumiller.mol.common.ui.input.validation.validate
import me.bumiller.mol.common.ui.viewmodel.MolViewModel

/**
 * View model for the login screen.
 */
class LoginViewModel(

    /**
     * The auth service
     */
    private val authService: AuthService

) : MolViewModel<LoginUiEvent, LoginEvent>() {

    init {
        registerUiState<LoginState>(LoginState())
    }

    /**
     * The form state.
     */
    val formState = uiState<LoginState>()

    override suspend fun handleEvent(event: LoginUiEvent) = with(event) {
        when (this) {
            is LoginUiEvent.ChangeEmail -> updateUiState<LoginState> {
                it.copy(
                    email = it.email.update(input)
                )
            }

            is LoginUiEvent.ChangePassword -> updateUiState<LoginState> {
                it.copy(
                    password = it.password.update(input)
                )
            }

            is LoginUiEvent.TogglePasswordVisibility -> updateUiState<LoginState> {
                it.copy(
                    passwordHidden = !it.passwordHidden
                )
            }

            is LoginUiEvent.Confirm -> handle()
            is LoginUiEvent.Back -> fireEvent(LoginEvent.Back)
            is LoginUiEvent.Signup -> fireEvent(LoginEvent.Signup)
        }
    }

    private suspend fun LoginUiEvent.Confirm.handle() {
        updateUiState<LoginState> {
            it.copy(
                email = formState.value.email.validate(),
                password = formState.value.password.validate()
            )
        }

        val isError = formState.value.run { email.isError() || password.isError() }
        if (isError) return

        val (email, password) = formState.value.run { email.value to password.value }

        clearErrors()
        val loginResponse = withFetchState {
            authService.login(email, password)
        }

        when (loginResponse) {
            // Successfully authentication
            is AuthResult.Success -> fireEvent(LoginEvent.LoggedIn)

            // Bad credentials
            is AuthResult.Error -> when (loginResponse.errorType) {
                LoginError.BadCredentials -> {
                    updateUiState<LoginState> {
                        it.copy(
                            email = it.email.copy(error = ValidationError.InvalidCredentials),
                            password = it.password.copy(error = ValidationError.InvalidCredentials)
                        )
                    }
                }

                else -> {}
            }

            is AuthResult.NetworkError -> hasNetworkError.emit(true)
            is AuthResult.UnknownError -> hasUnknownError.emit(true)
        }
    }

}