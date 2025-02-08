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

internal fun bookModel(bookEntity: BookEntity) = bookEntity.run {
    LawBook(id, key, name, description, isFavourite)
}

internal fun entryModel(entryEntity: EntryEntity) = entryEntity.run {
    LawEntry(id, parentBookId, name, key)
}

internal fun sectionModel(sectionEntity: SectionEntity) = sectionEntity.run {
    LawSection(id, parentEntryId, index, name, content)
}

internal fun invitationModel(invitationEntity: InvitationEntity) = invitationEntity.run {
    LawBookInvitation(
        id,
        authorId,
        recipientId,
        targetId,
        role(role),
        sentTimestamp,
        usedTimestamp,
        expiredTimestamp,
        invitationStatus(status),
        message
    )
}


internal fun foreignUserModel(foreignUserEntity: ForeignUserEntity) = foreignUserEntity.run {
    ForeignUser(id, username, firstName, lastName, gender(gender), birthday)
}

internal fun role(role: String) = when (role) {
    "member" -> MemberRole.Member
    "moderator" -> MemberRole.Moderator
    "admin" -> MemberRole.Admin
    else -> throw IllegalArgumentException()
}

internal fun invitationStatus(status: String) = when (status) {
    "open" -> InvitationStatus.Open
    "revoked" -> InvitationStatus.Revoked
    "accepted" -> InvitationStatus.Accepted
    "declined" -> InvitationStatus.Declined
    else -> throw IllegalArgumentException()
}

internal fun gender(gender: String) = when (gender) {
    "male" -> Gender.Male
    "female" -> Gender.Female
    "not_say" -> Gender.NotSay
    "other" -> Gender.Other
    else -> throw IllegalArgumentException()
}