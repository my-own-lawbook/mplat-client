package me.bumiller.mol.network.response

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Response class that contains information about a users profile
 */
@Serializable
data class UserProfileResponse(

    val birthday: LocalDate,

    val gender: String,

    val firstName: String,

    val lastName: String

)