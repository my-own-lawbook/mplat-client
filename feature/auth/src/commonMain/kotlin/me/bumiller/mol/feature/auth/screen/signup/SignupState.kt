package me.bumiller.mol.feature.auth.screen.signup

import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.common.ui.input.inputValue
import me.bumiller.mol.common.ui.input.validation.InputSemantic

/**
 * Ui state for the signup screen.
 */
data class SignupState(

    /**
     * The email input.
     */
    val email: InputValue<String> = inputValue("", InputSemantic.Email),

    /**
     * The username input.
     */
    val username: InputValue<String> = inputValue("", InputSemantic.Username),

    /**
     * The password input.
     */
    val password: InputValue<String> = inputValue("", InputSemantic.Password),

    /**
     * The password confirmation input.
     */
    val passwordConfirmation: InputValue<String> = inputValue("")

)