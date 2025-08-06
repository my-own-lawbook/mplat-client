package me.bumiller.civoris.model.user

import kotlinx.datetime.LocalDate

/**
 * Profile of a user.
 */
data class Profile(

    /**
     * The first name.
     */
    val firstName: String,

    /**
     * The last name.
     */
    val lastName: String,

    /**
     * The gender.
     */
    val gender: Gender,

    /**
     * The birthday.
     */
    val birthday: LocalDate

)
