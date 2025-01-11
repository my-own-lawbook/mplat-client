package me.bumiller.mol.feature.auth.screen.profile

import kotlinx.datetime.LocalDate
import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.common.ui.input.validation.InputSemantic
import me.bumiller.mol.model.Gender

/**
 * Ui State for the profile screen.
 */
internal data class ProfileUiState(

    /**
     * The input value for the first name.
     */
    val firstName: InputValue<String> = InputValue("", InputSemantic.Name),

    /**
     * The input value for the last name.
     */
    val lastName: InputValue<String> = InputValue("", InputSemantic.Name),

    /**
     * The input value for the gender.
     */
    val gender: InputValue<Gender?> = InputValue(null, InputSemantic.NotNull),

    /**
     * The input value for the birthday.
     */
    val birthday: InputValue<LocalDate?> = InputValue(null, InputSemantic.NotNull)

)