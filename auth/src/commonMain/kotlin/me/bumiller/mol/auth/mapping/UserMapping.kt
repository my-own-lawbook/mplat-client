package me.bumiller.mol.auth.mapping

import me.bumiller.mol.model.Gender
import me.bumiller.mol.model.user.AuthUser
import me.bumiller.mol.model.user.Profile
import me.bumiller.mol.network.response.AuthUserWithoutProfileResponse
import me.bumiller.mol.network.response.UserProfileResponse

internal fun AuthUserWithoutProfileResponse.toModel(): AuthUser =
    AuthUser(email, isEmailVerified, id, username)

internal fun UserProfileResponse.toModel(): Profile =
    Profile(firstName, lastName, gender.toGender(), birthday)

internal fun String.toGender(): Gender = when (this) {
    "male" -> Gender.Male
    "female" -> Gender.Female
    "other" -> Gender.Other
    "disclosed" -> Gender.NotSay
    else -> throw IllegalStateException("Invalid gender string: '$this'")
}