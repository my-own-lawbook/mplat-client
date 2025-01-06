package me.bumiller.mol.model.user

import me.bumiller.mol.model.Profile

/**
 * Special type of an [AuthUser] where the profile is also available.
 */
class AuthUserWithProfile(

    /**
     * The profile.
     */
    val profile: Profile,

    override val id: Long,
    override val username: String,
    override val email: String,
    override val isEmailVerified: Boolean


) : AuthUser(email, isEmailVerified, id, username) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AuthUserWithProfile

        if (profile != other.profile) return false
        if (id != other.id) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (isEmailVerified != other.isEmailVerified) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + profile.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + isEmailVerified.hashCode()
        return result
    }

    override fun toString(): String {
        return "AuthUserWithProfile(profile=$profile, id=$id, username='$username', email='$email', isEmailVerified=$isEmailVerified)"
    }
}