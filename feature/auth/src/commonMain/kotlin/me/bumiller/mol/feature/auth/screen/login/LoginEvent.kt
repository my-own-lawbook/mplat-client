package me.bumiller.mol.feature.auth.screen.login

import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent

/**
 * Events fired from the login screen.
 */
sealed interface LoginUiEvent : UiEvent {

    /**
     * User changed the email in the text field.
     *
     * @param input The new email.
     */
    data class ChangeEmail(val input: String) : LoginUiEvent

    /**
     * User changed the password in the text field.
     *
     * @param input The new password.
     */
    data class ChangePassword(val input: String) : LoginUiEvent

    /**
     * User changed the visibility of the password.
     */
    data object TogglePasswordVisibility : LoginUiEvent

    /**
     * User clicked on the back button.
     */
    data object Back : LoginUiEvent

    /**
     * User clocked on the continue button.
     */
    data object Confirm : LoginUiEvent

    /**
     * User clicked on the link to the signup screen.
     */
    data object Signup : LoginUiEvent

}

/**
 * Events fired by the login view model.
 */
sealed interface LoginEvent : ViewModelEvent {

    /**
     * Will navigate back to the welcome screen.
     */
    data object Back : LoginEvent

    /**
     * Logged the user in.
     */
    data class LoggedIn(

        /**
         * Whether the email of the logged in used is verified.
         */
        val isEmailVerified: Boolean,

        /**
         * Whether the profile of the logged in user is set.
         */
        val hasProfileSet: Boolean

    ) : LoginEvent

    /**
     * Will navigate to the signup screen.
     */
    data object Signup : LoginEvent

}