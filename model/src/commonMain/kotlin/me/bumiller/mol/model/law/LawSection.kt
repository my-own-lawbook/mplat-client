package me.bumiller.mol.model.law

import me.bumiller.mol.model.Identifiable

/**
 * Models one part of a [LawEntry] that contains actual content.
 */
data class LawSection(

    override val id: Long,

    /**
     * Id of the parent [LawEntry].
     */
    val parentEntryId: Long,

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