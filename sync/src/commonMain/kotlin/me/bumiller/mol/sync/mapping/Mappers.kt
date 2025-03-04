package me.bumiller.mol.sync.mapping

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.entities.EntryEntity
import me.bumiller.mol.database.entities.ForeignUserEntity
import me.bumiller.mol.database.entities.InvitationEntity
import me.bumiller.mol.database.entities.SectionEntity
import me.bumiller.mol.network.response.BookInvitationResponse
import me.bumiller.mol.network.response.ForeignUserResponse
import me.bumiller.mol.network.response.LawBookResponse
import me.bumiller.mol.network.response.LawEntryResponse
import me.bumiller.mol.network.response.LawSectionResponse

internal val bookMapper = EntityMapper<LawBookResponse, BookEntity> { response, entity ->
    BookEntity(
        response.id,
        response.key,
        response.name,
        response.description,
        entity?.isFavourite ?: false
    )
}

internal val entryMapper =
    ParentEntityMapper<LawEntryResponse, EntryEntity> { response, _, parentId ->
        EntryEntity(response.id, parentId[0], response.key, response.name)
    }

internal val sectionMapper =
    ParentEntityMapper<LawSectionResponse, SectionEntity> { response, _, parentId ->
        SectionEntity(response.id, parentId[0], response.index, response.name, response.content)
    }

internal val userMapper =
    EntityMapper<ForeignUserResponse, ForeignUserEntity> { response, _ ->
        ForeignUserEntity(
            response.id,
            response.username,
            response.profile.firstName,
            response.profile.lastName,
            response.profile.gender,
            response.profile.birthday.let(LocalDate::parse)
        )
    }

internal val invitationMapper =
    EntityMapper<BookInvitationResponse, InvitationEntity> { response, _ ->
        InvitationEntity(
            response.id,
            response.authorId,
            response.recipientId,
            response.targetBookId,
            response.role,
            Instant.parse(response.sentAt),
            response.usedAt?.let(Instant::parse),
            response.expiredAt?.let(Instant::parse),
            response.status,
            response.message
        )
    }