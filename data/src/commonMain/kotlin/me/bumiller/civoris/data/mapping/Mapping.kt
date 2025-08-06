package me.bumiller.civoris.data.mapping

import me.bumiller.civoris.database.entities.BookEntity
import me.bumiller.civoris.database.entities.EntryEntity
import me.bumiller.civoris.database.entities.ForeignUserEntity
import me.bumiller.civoris.database.entities.InvitationEntity
import me.bumiller.civoris.database.entities.SectionEntity
import me.bumiller.civoris.model.law.ForeignUser
import me.bumiller.civoris.model.law.InvitationStatus
import me.bumiller.civoris.model.law.LawBook
import me.bumiller.civoris.model.law.LawBookInvitation
import me.bumiller.civoris.model.law.LawEntry
import me.bumiller.civoris.model.law.LawSection
import me.bumiller.civoris.model.law.MemberRole
import me.bumiller.civoris.model.user.Gender

internal fun mapBookModel(bookEntity: BookEntity) = bookEntity.run {
    LawBook(id, key, name, description, isFavourite, isMember)
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
    "Member" -> MemberRole.Member
    "Moderator" -> MemberRole.Moderator
    "Admin" -> MemberRole.Admin
    else -> throw IllegalArgumentException()
}

internal fun mapInvitationStatus(status: String) = when (status) {
    "Open" -> InvitationStatus.Open
    "Revoked" -> InvitationStatus.Revoked
    "Accepted" -> InvitationStatus.Accepted
    "Declined" -> InvitationStatus.Declined
    else -> throw IllegalArgumentException()
}

internal fun mapGender(gender: String) = when (gender) {
    "male" -> Gender.Male
    "female" -> Gender.Female
    "not_say" -> Gender.NotSay
    "other" -> Gender.Other
    else -> throw IllegalArgumentException()
}