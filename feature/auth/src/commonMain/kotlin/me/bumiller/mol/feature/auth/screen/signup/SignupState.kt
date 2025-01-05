package me.bumiller.mol.feature.auth.screen.signup

import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.common.ui.input.validation.InputSemantic

/**
 * Ui state for the signup screen.
 */
data class SignupState(

    /**
     * The email input.
     */
    val email: InputValue<String> = InputValue("", InputSemantic.Email),

    /**
     * The username input.
     */
    val username: InputValue<String> = InputValue("", InputSemantic.Username),

    /**
     * The password input.
     */
    val password: InputValue<String> = InputValue("", InputSemantic.Password),

    /**
     * The password confirmation input.
     */
    val passwordConfirmation: InputValue<String> = InputValue("")

)