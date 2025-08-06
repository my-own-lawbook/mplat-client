package me.bumiller.civoris.feature.auth.screen.login

import me.bumiller.civoris.common.ui.input.InputValue
import me.bumiller.civoris.common.ui.input.inputValue
import me.bumiller.civoris.common.ui.input.validation.InputSemantic

/**
 * Ui state for the login view model.
 */
data class LoginState(

    /**
     * The input for the email field.
     */
    val email: InputValue<String> = inputValue("", InputSemantic.NonEmpty),

    /**
     * The input value for the password field.
     */
    val password: InputValue<String> = inputValue("", InputSemantic.NonEmpty),

    /**
     * Whether the password is currently hidden.
     */
    val passwordHidden: Boolean = true

)