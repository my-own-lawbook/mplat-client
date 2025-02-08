package me.bumiller.mol.data.mapping

import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.entities.SectionEntity
import me.bumiller.mol.model.law.ForeignUser
import me.bumiller.mol.model.law.InvitationStatus
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.law.LawBookInvitation
import me.bumiller.mol.model.law.LawEntry
import me.bumiller.mol.model.law.LawSection
import me.bumiller.mol.model.law.MemberRole
import me.bumiller.mol.model.user.Gender

internal fun mapBookModel(bookEntity: BookEntity) = bookEntity.run {
    LawBook(id, key, name, description, isFavourite)
}

internal fun mapEntryModel(entryEntity: EntryEntity) = entryEntity.run {
    LawEntry(id, parentBookId, name, key)
}

internal fun mapSectionModel(sectionEntity: SectionEntity) = sectionEntity.run {
    LawSection(id, parentEntryId, index, name, content)
}

internal fun mapInvitationModel(invitationEntity: InvitationEntity) = invitationEntity.run {
    LawBookInvitation(
        id,
        authorId,
        recipientId,
        targetId,
        mapRole(role),
        sentTimestamp,
        usedTimestamp,
        expiredTimestamp,
        mapInvitationStatus(status),
        message
    )
}


internal fun mapForeignUserModel(foreignUserEntity: ForeignUserEntity) = foreignUserEntity.run {
    ForeignUser(id, username, firstName, lastName, mapGender(gender), birthday)
}

internal fun mapRole(role: String) = when (role) {
    "member" -> MemberRole.Member
    "moderator" -> MemberRole.Moderator
    "admin" -> MemberRole.Admin
    else -> throw IllegalArgumentException()
}

internal fun mapInvitationStatus(status: String) = when (status) {
    "open" -> InvitationStatus.Open
    "revoked" -> InvitationStatus.Revoked
    "accepted" -> InvitationStatus.Accepted
    "declined" -> InvitationStatus.Declined
    else -> throw IllegalArgumentException()
}

internal fun mapGender(gender: String) = when (gender) {
    "male" -> Gender.Male
    "female" -> Gender.Female
    "not_say" -> Gender.NotSay
    "other" -> Gender.Other
    else -> throw IllegalArgumentException()
}