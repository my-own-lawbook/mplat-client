package me.bumiller.civoris.feature.auth.screen.profile

import kotlinx.datetime.LocalDate
import me.bumiller.civoris.common.ui.input.InputValue
import me.bumiller.civoris.common.ui.input.inputValue
import me.bumiller.civoris.common.ui.input.validation.InputSemantic
import me.bumiller.civoris.model.user.Gender

/**
 * Ui State for the profile screen.
 */
internal data class ProfileUiState(

    /**
     * The input value for the first name.
     */
    val firstName: InputValue<String> = inputValue("", InputSemantic.Name),

    /**
     * The input value for the last name.
     */
    val lastName: InputValue<String> = inputValue("", InputSemantic.Name),

    /**
     * The input value for the gender.
     */
    val gender: InputValue<Gender?> = inputValue(
        null,
        InputSemantic.NotNull,
        InputSemantic.NotInFuture
    ),

    /**
     * The input value for the birthday.
     */
    val birthday: InputValue<LocalDate?> = inputValue(
        null,
        InputSemantic.NotNull,
        InputSemantic.NotInFuture
    )

)