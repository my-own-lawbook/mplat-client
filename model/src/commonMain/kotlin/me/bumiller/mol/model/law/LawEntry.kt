package me.bumiller.mol.model.law

import me.bumiller.mol.model.Identifiable

/**
 * Models one part of a [LawBook].
 */
data class LawEntry(

    override val id: Long,

    /**
     * Id of the parent [LawBook].
     */
    val parentBookId: Long,

    /**
     * Name of the entry.
     */
    val name: String,

    /**
     * Key of the entry, i.e. shorthand for [name].
     */
    val key: String


) : Identifiable<Long>