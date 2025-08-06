package me.bumiller.civoris.feature.auth.screen.signup

import me.bumiller.civoris.common.ui.event.UiEvent
import me.bumiller.civoris.feature.auth.screen.SignupStageEvent

/**
 * Events fired by the signup screen.
 */
internal sealed interface SignupUiEvent : UiEvent {

    /**
     * User changed the email input.
     */
    data class ChangeEmail(val input: String) : SignupUiEvent

    /**
     * User changed the username input.
     */
    data class ChangeUsername(val input: String) : SignupUiEvent

    /**
     * User changed the password input.
     */
    data class ChangePassword(val input: String) : SignupUiEvent

    /**
     * User changed the password confirmation input.
     */
    data class ChangePasswordConfirmation(val input: String) : SignupUiEvent

    /**
     * User clicked the back button.
     */
    data object Back : SignupUiEvent

    /**
     * User clicked the login link.
     */
    data object Login : SignupUiEvent

    /**
     * User confirmed the credentials.
     */
    data object Confirm : SignupUiEvent

}

/**
 * Events fired by the signup view model.
 */
internal sealed interface SignupEvent : SignupStageEvent {

    /**
     * Will return to previous screen.
     */
    data object Back : SignupEvent

    /**
     * Proceeding to the login screen.
     */
    data object Login : SignupEvent

}