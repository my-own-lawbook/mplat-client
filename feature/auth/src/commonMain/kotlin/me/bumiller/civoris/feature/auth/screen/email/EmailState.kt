package me.bumiller.civoris.feature.auth.screen.email

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.bumiller.civoris.common.ui.input.InputValue
import me.bumiller.civoris.common.ui.input.inputValue

/**
 * State for the email-verify screen.
 */
data class EmailState(

    /**
     * The token entered by the user.
     */
    val token: InputValue<String> = inputValue(""),

    /**
     * State in time of the next resend.
     */
    val nextResendAt: Instant = Clock.System.now(),

    /**
     * Whether the OTP was prefilled due to the deep-link.
     */
    val isOtpPrefilled: Boolean = false

)