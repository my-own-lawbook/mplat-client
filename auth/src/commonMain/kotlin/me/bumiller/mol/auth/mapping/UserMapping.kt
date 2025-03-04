package me.bumiller.mol.auth.mapping

import kotlinx.datetime.LocalDate
import me.bumiller.mol.model.user.AuthUser
import me.bumiller.mol.model.user.AuthUserWithProfile
import me.bumiller.mol.model.user.Gender
import me.bumiller.mol.model.user.Profile
import me.bumiller.mol.network.response.AuthUserWithProfileResponse
import me.bumiller.mol.network.response.AuthUserWithoutProfileResponse
import me.bumiller.mol.network.response.UserProfileResponse

internal fun AuthUserWithoutProfileResponse.toModel(): AuthUser =
    AuthUser(email, isEmailVerified, id, username)

internal fun UserProfileResponse.toModel(): Profile =
    Profile(firstName, lastName, gender.toGender(), LocalDate.parse(birthday))

internal fun AuthUserWithProfileResponse.toModel(): AuthUserWithProfile =
    AuthUserWithProfile(profile.toModel(), id, username, email, isEmailVerified)

internal fun Gender.toRequestString(): String = when (this) {
    Gender.Female -> "female"
    Gender.Male -> "male"
    Gender.NotSay -> "disclosed"
    Gender.Other -> "other"
}

internal fun String.toGender(): Gender = when (this) {
    "male" -> Gender.Male
    "female" -> Gender.Female
    "other" -> Gender.Other
    "disclosed" -> Gender.NotSay
    else -> error("Invalid gender string: '$this'")
}