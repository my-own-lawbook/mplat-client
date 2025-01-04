package me.bumiller.mol.feature.auth.screen.login

import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.common.ui.input.validation.InputSemantic

/**
 * Ui state for the login view model.
 */
data class LoginState(

    /**
     * The input for the email field.
     */
    val email: InputValue<String> = InputValue("", InputSemantic.Email),

    /**
     * The input value for the password field.
     */
    val password: InputValue<String> = InputValue("", InputSemantic.Password),

    /**
     * Whether the password is currently hidden.
     */
    val passwordHidden: Boolean = true

)