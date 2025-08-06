package me.bumiller.civoris.model.user

/**
 * Special type of a [User] where the profile is also available.
 */
class UserWithProfile(

    /**
     * The profile.
     */
    val profile: Profile,

    override val id: Long,
    override val username: String

) : User(id, username) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as UserWithProfile

        if (profile != other.profile) return false
        if (id != other.id) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + profile.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + username.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserWithProfile(profile=$profile, id=$id, username='$username')"
    }
}