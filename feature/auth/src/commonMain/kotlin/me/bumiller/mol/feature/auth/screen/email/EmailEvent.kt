package me.bumiller.mol.feature.auth.screen.email

import me.bumiller.mol.common.ui.event.UiEvent

/**
 * Events fired by the email screen.
 */
internal sealed interface EmailUiEvent : UiEvent {

    /**
     * User changed the token input
     */
    data class ChangeToken(val input: String) : EmailUiEvent

    /**
     * User clicked to check the token.
     */
    data object Continue : EmailUiEvent

    /**
     * User clicked to resend the email.
     */
    data object Resend : EmailUiEvent

}