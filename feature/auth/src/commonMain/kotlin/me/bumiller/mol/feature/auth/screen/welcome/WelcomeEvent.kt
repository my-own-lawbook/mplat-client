package me.bumiller.mol.feature.auth.screen.welcome

import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent

/**
 * Events that get fired from the [WelcomeScreen].
 */
sealed interface WelcomeUiEvent : UiEvent {

    /**
     * User proceeds by logging in
     */
    data object ContinueLogin : WelcomeUiEvent

    /**
     * User proceeds by signing up
     */
    data object ContinueSignup : WelcomeUiEvent

}

/**
 * Events that get fired from the [WelcomeViewModel].
 */
sealed interface WelcomeEvent : ViewModelEvent {

    /**
     * User proceeds by logging in
     */
    data object ContinueLogin : WelcomeEvent

    /**
     * User proceeds by signing up
     */
    data object ContinueSignup : WelcomeEvent

}