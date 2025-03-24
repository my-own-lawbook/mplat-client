package me.bumiller.mol.feature.auth.screen

import androidx.lifecycle.viewModelScope
import me.bumiller.mol.auth.AuthResult
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.auth.GetProfileError
import me.bumiller.mol.auth.LoginError
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.feature.auth.model.SignupStage
import me.bumiller.mol.model.user.AuthUser
import me.bumiller.mol.model.user.AuthUserWithProfile
import me.bumiller.mol.model.user.Profile

/**
 * Viewmodel for the signup-screens that perform background checking.
 */
internal abstract class SignupStageViewmodel<UiEvent : me.bumiller.mol.common.ui.event.UiEvent>(

    /**
     * The signup stage this viewmodel is handling.
     */
    private val signupStage: SignupStage,

    /**
     * The auth service.
     */
    private val authService: AuthService

) : MolViewModel<UiEvent, SignupStageEvent>() {

    private val taskScheduler = TaskScheduler(
        delayMillis = 5_000,
        defaultArg = false,
        task = ::performStageCheck,
        scope = viewModelScope
    )

    init {
        taskScheduler.start()
    }

    protected fun requestStageCheck() {
        taskScheduler.schedule(true)
    }

    /**
     * Performs a check that checks whether the current signup stage is still valid or out of date.
     *
     * @param visible Whether the requests should be made visible to the user
     */
    private suspend fun performStageCheck(visible: Boolean) {
        val stage = checkCurrentStage(visible)
        if (stage !in setOf(null, signupStage)) {
            fireEvent(SignupStageEvent.SignupStageChanged(stage!!))
            taskScheduler.stop()
        }
    }

    private suspend fun checkCurrentStage(visible: Boolean): SignupStage? {
        var networkError = false
        var user: AuthUser? = null

        suspend fun <Data, Error> performRequest(request: suspend () -> AuthResult<Data, Error>) =
            if (visible) withFetchState { request() } else request()

        val loginResult = performRequest { authService.login() }
        when (loginResult) {
            is AuthResult.NetworkError -> networkError = true
            is AuthResult.Success<AuthUser, LoginError> -> user = loginResult.data
            else -> {}
        }

        if (user != null) {
            val profileResult = performRequest { authService.getProfile() }
            when (profileResult) {
                is AuthResult.NetworkError -> networkError = true
                is AuthResult.Success<Profile, GetProfileError> -> user = AuthUserWithProfile(
                    profile = profileResult.data,
                    id = user.id,
                    username = user.username,
                    email = user.email,
                    isEmailVerified = user.isEmailVerified
                )

                else -> {}
            }
        }

        return if (networkError) null else SignupStage.fromUser(user)
    }

}