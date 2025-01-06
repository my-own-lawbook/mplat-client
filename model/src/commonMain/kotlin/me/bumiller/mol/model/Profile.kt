package me.bumiller.mol.model

import kotlinx.datetime.LocalDateTime

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
    val birthday: LocalDateTime

)
