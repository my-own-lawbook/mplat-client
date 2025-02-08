package me.bumiller.mol.model.law

import me.bumiller.mol.model.Identifiable

/**
 * Models one entry in a [LawBook].
 */
data class LawEntry(

    override val id: Long,

    /**
     * Id of the parent [LawBook].
     */
    val parentBookId: Long,

    /**
     * Index resembling a position inside the entry.
     */
    val index: String,

    /**
     * The name of the section.
     */
    val name: String,

    /**
     * The content of the section.
     */
    val content: String

) : Identifiable<Long>
