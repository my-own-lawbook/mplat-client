package me.bumiller.civoris.model.user

/**
 * Base class for any user.
 */
open class User(

    /**
     * The users id.
     */
    open val id: Long,

    /**
     * The username.
     */
    open val username: String

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + username.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, username='$username')"
    }
}