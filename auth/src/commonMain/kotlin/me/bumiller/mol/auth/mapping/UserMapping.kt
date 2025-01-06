package me.bumiller.mol.auth.mapping

import me.bumiller.mol.model.user.AuthUser
import me.bumiller.mol.network.response.AuthUserWithoutProfileResponse

internal fun AuthUserWithoutProfileResponse.toModel(): AuthUser =
    AuthUser(email, isEmailVerified, id, username)