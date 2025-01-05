package me.bumiller.mol.feature.auth.screen.email

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.bumiller.mol.common.ui.input.InputValue

/**
 * State for the email-verify screen.
 */
data class EmailState(

    /**
     * The token entered by the user.
     */
    val token: InputValue<String> = InputValue(""),

    /**
     * State in time of the next resend.
     */
    val nextResendAt: Instant = Clock.System.now()

)