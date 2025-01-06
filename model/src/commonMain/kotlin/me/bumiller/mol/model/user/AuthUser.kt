package me.bumiller.mol.model.user

/**
 * Special type of user when it is the user the app is authenticating with.
 */
open class AuthUser(

    /**
     * The email of the user.
     */
    open val email: String,

    /**
     * Whether the user has the email verified.
     */
    open val isEmailVerified: Boolean,

    override val id: Long,
    override val username: String

) : User(id, username) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as AuthUser

        if (email != other.email) return false
        if (isEmailVerified != other.isEmailVerified) return false
        if (id != other.id) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + isEmailVerified.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + username.hashCode()
        return result
    }

    override fun toString(): String {
        return "AuthUser(email='$email', isEmailVerified=$isEmailVerified, id=$id, username='$username')"
    }
}