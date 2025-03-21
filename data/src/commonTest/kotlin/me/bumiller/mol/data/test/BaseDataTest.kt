package me.bumiller.mol.data.test

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import me.bumiller.mol.data.mapping.mapBookModel
import me.bumiller.mol.data.mapping.mapEntryModel
import me.bumiller.mol.data.mapping.mapForeignUserModel
import me.bumiller.mol.data.mapping.mapInvitationModel
import me.bumiller.mol.data.mapping.mapSectionModel
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.entities.SectionEntity
import me.bumiller.mol.model.law.ForeignUser
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.law.LawBookInvitation
import me.bumiller.mol.model.law.LawEntry
import me.bumiller.mol.model.law.LawSection

/**
 * Base class for any test testing components of the data module.
 */
abstract class BaseDataTest {


    /**
     * Creates a [BookEntity].
     *
     * @param key The key for uniqueness
     * @return The [BookEntity]
     */
    fun bookEntity(key: Long) = BookEntity(
        id = key,
        key = "key $key",
        name = "name $key",
        description = "description $key",
        isFavourite = key % 2L == 0L,
        isMember = key % 2L == 1L
    )

    /**
     * Creates a [LawBook].
     *
     * @param key The key for uniqueness
     * @return The [LawBook]
     */
    fun bookModel(key: Long) = mapBookModel(bookEntity(key))

    /**
     * Creates a [EntryEntity].
     *
     * @param key The key for uniqueness
     * @return The [EntryEntity]
     */
    fun entryEntity(key: Long) = EntryEntity(
        id = key,
        parentBookId = key,
        key = "key $key",
        name = "name $key"
    )

    /**
     * Creates a [LawEntry].
     *
     * @param key The key for uniqueness
     * @return The [LawEntry]
     */
    fun entryModel(key: Long) = mapEntryModel(entryEntity(key))

    /**
     * Creates a [ForeignUserEntity].
     *
     * @param key The key for uniqueness
     * @return The [ForeignUserEntity]
     */
    fun foreignUserEntity(key: Long) = ForeignUserEntity(
        id = key,
        username = "username $key",
        firstName = "firstName $key",
        lastName = "lastName $key",
        gender = modOptions(key, listOf("male", "female", "not_say", "other")),
        birthday = LocalDate.fromEpochDays(1000 + key.toInt())
    )

    /**
     * Creates a [ForeignUser].
     *
     * @param key The key for uniqueness
     * @return The [ForeignUser]
     */
    fun foreignUser(key: Long) = mapForeignUserModel(foreignUserEntity(key))

    /**
     * Creates an [InvitationEntity].
     *
     * @param key The key for uniqueness
     * @return The [InvitationEntity]
     */
    fun invitationEntity(key: Long) = InvitationEntity(
        id = key,
        authorId = key,
        recipientId = key,
        targetId = key,
        role = modOptions(key, listOf("member", "moderator", "admin")),
        sentTimestamp = Instant.fromEpochSeconds(1000 + key),
        usedTimestamp = Instant.fromEpochSeconds(1000 + key),
        expiredTimestamp = Instant.fromEpochSeconds(1000 + key),
        status = modOptions(key, listOf("open", "accepted", "declined", "revoked")),
        message = "message $key"
    )

    /**
     * Creates a [LawBookInvitation].
     *
     * @param key The key for uniqueness
     * @return The [LawBookInvitation]
     */
    fun invitationModel(key: Long) = mapInvitationModel(invitationEntity(key))

    /**
     * Creates a [SectionEntity].
     *
     * @param key The key for uniqueness
     * @return The [SectionEntity]
     */
    fun sectionEntity(key: Long) = SectionEntity(
        id = key,
        parentEntryId = key,
        index = "index $key",
        name = "name $key",
        content = "content $key"
    )

    /**
     * Creates a [LawSection].
     *
     * @param key The key for uniqueness
     * @return The [LawSection]
     */
    fun sectionModel(key: Long) = mapSectionModel(sectionEntity(key))

    private fun <Data> modOptions(key: Long, list: List<Data>) = list[(key % list.size).toInt()]

}