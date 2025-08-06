package me.bumiller.civoris.model.law

import kotlinx.datetime.LocalDate
import me.bumiller.civoris.model.Identifiable
import me.bumiller.civoris.model.user.Gender

/**
 * Models a user. This is not specifically the logged in user, but any other user in the system that we have access to.
 */
data class ForeignUser(

    override val id: Long,

    /**
     * The username of the user.
     */
    val username: String,

    /**
     * The first name of the user.
     */
    val firstName: String,

    /**
     * The last name of the user.
     */
    val lastName: String,

    /**
     * The gender of the user.
     */
    val gender: Gender,

    /**
     * The birthday of the user
     */
    val birthday: LocalDate

) : Identifiable<Long>