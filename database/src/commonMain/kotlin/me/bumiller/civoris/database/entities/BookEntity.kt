package me.bumiller.civoris.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.bumiller.civoris.database.entities.base.SimpleEntity

/**
 * Entity that models one record in the 'books' table.
 */
@Entity(
    tableName = "books"
)
data class BookEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    /**
     * The shorthand for the book.
     */
    val key: String,

    /**
     * The full name of the book.
     */
    val name: String,

    /**
     * The description of the book.
     */
    val description: String,

    /**
     * Whether the book has been marked as favourite
     */
    val isFavourite: Boolean,

    /**
     * Whether the user is a member of that book (true), or only has temporary access to it due to an invitation (false)
     */
    val isMember: Boolean

) : SimpleEntity
