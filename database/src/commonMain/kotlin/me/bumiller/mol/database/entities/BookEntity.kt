package me.bumiller.mol.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.bumiller.mol.database.entities.base.SimpleEntity

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
    val isFavourite: Boolean

) : SimpleEntity
