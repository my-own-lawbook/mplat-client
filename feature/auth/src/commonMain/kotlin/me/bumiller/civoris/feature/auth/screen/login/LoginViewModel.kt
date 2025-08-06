package me.bumiller.civoris.feature.auth.screen.login

import kotlinx.coroutines.flow.asStateFlow
import me.bumiller.civoris.auth.AuthResult
import me.bumiller.civoris.auth.AuthService
import me.bumiller.civoris.auth.GetProfileError
import me.bumiller.civoris.auth.LoginError
import me.bumiller.civoris.common.ui.input.validation.ValidationError
import me.bumiller.civoris.common.ui.input.validation.validate
import me.bumiller.civoris.common.ui.viewmodel.CivorisViewModel
import me.bumiller.civoris.model.user.Profile

/**
 * View model for the login screen.
 */
class LoginViewModel(

    /**
     * The auth service
     */
    private val authService: AuthService

) : CivorisViewModel<LoginUiEvent, LoginEvent>() {

    init {
        registerUiState<LoginState>(LoginState())
    }

    /**
     * The form state.
     */
    val formState = uiState<LoginState>().asStateFlow()

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
        val (loginResponse, profileResponse) = withFetchState {
            authService.login(email, password) to authService.getProfile()
        }

        when (loginResponse) {
            // Successfully authentication
            is AuthResult.Success -> handleLoginSuccess(
                loginResponse.data.isEmailVerified,
                profileResponse
            )

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

            is AuthResult.NetworkError -> setHasNetworkError()
            is AuthResult.UnknownError -> setHasUnknownError()
        }
    }

    private suspend fun handleLoginSuccess(
        isEmailVerified: Boolean,
        profileResponse: AuthResult<Profile, GetProfileError>
    ) {
        val event = when (profileResponse) {
            is AuthResult.Success -> LoginEvent.LoggedIn(isEmailVerified, true)
            is AuthResult.Error -> LoginEvent.LoggedIn(isEmailVerified, false)
            else -> null
        }

        val isUnknownError = (profileResponse is AuthResult.Error &&
                profileResponse.errorType == GetProfileError.NotAuthenticated) ||
                profileResponse is AuthResult.UnknownError

        val isNetworkError = profileResponse is AuthResult.NetworkError

        event?.let { fireEvent(it) }
        setHasNetworkError(isNetworkError)
        setHasUnknownError(isUnknownError)
    }

}