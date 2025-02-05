package me.bumiller.mol.database.entities

import androidx.room.Entity
import androidx.room.Ignore
import me.bumiller.mol.database.entities.base.CrossrefEntity

/**
 * Entity that represents a record in the 'books_x_foreign_users' table.
 */
@Entity(
    primaryKeys = ["bookId", "userId"]
)
data class BookMemberCrossref(

    /**
     * The id of the book.
     */
    val bookId: Long,

    /**
     * The id of the user.
     */
    val userId: Long,

    /**
     * The role of the user in the book. One of the following:
     * - admin
     * - moderator
     * - member
     */
    val role: String,

    @Ignore
    override val parentId: Long = bookId,

    @Ignore
    override val childId: Long = userId

) : CrossrefEntity
