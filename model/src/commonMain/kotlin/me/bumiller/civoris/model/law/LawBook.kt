package me.bumiller.civoris.model.law

import me.bumiller.civoris.model.Identifiable

/**
 * Model for a law-book.
 */
data class LawBook(

    override val id: Long,

    /**
     * The key, i.e. a shorthand for the book.
     */
    val key: String,

    /**
     * The name of the book.
     */
    val name: String,

    /**
     * A description about the content of the book.
     */
    val description: String,

    /**
     * Whether the book is marked as favourite
     */
    val isFavourite: Boolean,

    /**
     * Whether the user is a member of this book(true) or only has temporary access to it (false)
     */
    val isMember: Boolean

) : Identifiable<Long>
