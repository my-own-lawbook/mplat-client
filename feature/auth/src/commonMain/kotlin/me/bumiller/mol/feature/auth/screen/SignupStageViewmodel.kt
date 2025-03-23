package me.bumiller.mol.feature.auth.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private var performBackgroundChecks = true

    init {
        viewModelScope.launch {
            while (performBackgroundChecks) {
                performStageCheck(false)
                delay(STAGE_CHECK_DELAY_MILLIS)
            }
        }
    }

    /**
     * Performs a check that checks whether the current signup stage is still valid or out of date.
     *
     * @param visible Whether the requests should be made visible to the user
     */
    protected suspend fun performStageCheck(visible: Boolean) {
        val stage = checkCurrentStage(visible)
        if (stage != signupStage) {
            performBackgroundChecks = false
            fireEvent(SignupStageEvent.SignupStageChanged(stage))
        }
    }

    private suspend fun checkCurrentStage(visible: Boolean): SignupStage {
        var user: AuthUser? = null

        suspend fun <Data, Error> performRequest(request: suspend () -> AuthResult<Data, Error>) =
            if (visible) withFetchState { request() } else request()

        when (val loginResult = performRequest { authService.login() }) {
            is AuthResult.Success<AuthUser, LoginError> -> user = loginResult.data
            else -> {}
        }

        if (user != null) {
            when (val profileResult = performRequest { authService.getProfile() }) {
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

        return SignupStage.fromUser(user)
    }

    companion object {

        private const val STAGE_CHECK_DELAY_MILLIS = 5000L

    }

}