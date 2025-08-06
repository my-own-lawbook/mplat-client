package me.bumiller.civoris.data.mapping

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import me.bumiller.civoris.data.test.BaseDataTest
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
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Tests for testing the mapping.
 */
class MappingTest : BaseDataTest() {

    @Test
    @DisplayName("Mapping a BookEntity to a LawBook copies the right attributes")
    fun mapBookModelWorks() {
        val entity = BookEntity(45L, "key 3", "name 24", "description 21", false, false)
        val model = mapBookModel(entity)

        Assertions.assertEquals(
            LawBook(45L, "key 3", "name 24", "description 21", false, false),
            model
        )
    }

    @Test
    @DisplayName("Mapping an EntryEntity to a LawEntry copies the right attributes")
    fun mapEntryModelWorks() {
        val entity = EntryEntity(45L, 2L, "key 24", "name 21")
        val model = mapEntryModel(entity)

        Assertions.assertEquals(LawEntry(45L, 2L, "name 21", "key 24"), model)
    }

    @Test
    @DisplayName("Mapping a SectionEntity to a LawSection copies the right attributes")
    fun mapSectionModelWorks() {
        val entity = SectionEntity(45L, 2L, "index 24", "name 21", "content 43")
        val model = mapSectionModel(entity)

        Assertions.assertEquals(LawSection(45L, 2L, "index 24", "name 21", "content 43"), model)
    }

    @Test
    @DisplayName("Mapping a ForeignUserEntity to a ForeignUser copies the right attributes")
    fun mapForeignUserModelWorks() {
        val entity = ForeignUserEntity(
            23L,
            "username 98",
            "firstName 32",
            "lastName 39",
            "male",
            LocalDate(1, 1, 1)
        )
        val model = mapForeignUserModel(entity)

        Assertions.assertEquals(
            ForeignUser(
                23L,
                "username 98",
                "firstName 32",
                "lastName 39",
                Gender.Male,
                LocalDate(1, 1, 1)
            ), model
        )
    }

    @Test
    @DisplayName("Mapping a InvitationEntity to a LawBookInvitation copies the right attributes")
    fun mapInvitationModelWorks() {
        val entity = InvitationEntity(
            22L,
            4L,
            1L,
            7L,
            "admin",
            Instant.fromEpochSeconds(23L),
            null,
            null,
            "open",
            null
        )
        val model = mapInvitationModel(entity)

        Assertions.assertEquals(
            LawBookInvitation(
                22L,
                4L,
                1L,
                7L,
                MemberRole.Admin,
                Instant.fromEpochSeconds(23L),
                null,
                null,
                InvitationStatus.Open,
                null
            ), model
        )
    }

    @Test
    @DisplayName("Mapping strings to gender returns the correct gender")
    fun mapGenderReturnsCorrectGender() {
        val values = mapOf(
            "male" to Gender.Male,
            "female" to Gender.Female,
            "not_say" to Gender.NotSay,
            "other" to Gender.Other
        )

        values.forEach { (string, expected) ->
            val returned = mapGender(string)
            Assertions.assertEquals(expected, returned)
        }
    }

    @Test
    @DisplayName("Mapping strings to gender throws if an invalid string was passed")
    fun mapGenderThrowsForInvalidString() {
        val values = listOf("malee", "ffemale", "male_femae", "kdjd", "")

        values.forEach { string ->
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                mapGender(string)
            }
        }
    }

    @Test
    @DisplayName("Mapping strings to invitation statuses returns the correct status")
    fun mapInvitationStatusWorks() {
        val values = mapOf(
            "open" to InvitationStatus.Open,
            "declined" to InvitationStatus.Declined,
            "revoked" to InvitationStatus.Revoked,
            "accepted" to InvitationStatus.Accepted
        )

        values.forEach { (string, expected) ->
            val returned = mapInvitationStatus(string)
            Assertions.assertEquals(expected, returned)
        }
    }

    @Test
    @DisplayName("Mapping strings to invitation status throws if an invalid string was passed")
    fun mapInvitationStatusThrowsInvalidString() {
        val values = listOf("oppen", "closed", "acccepted", "kdjd", "")

        values.forEach { string ->
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                mapInvitationStatus(string)
            }
        }
    }

    @Test
    @DisplayName("Mapping strings to member roles returns the correct status")
    fun mapRoleReturnsCorrectRole() {
        val values = mapOf(
            "member" to MemberRole.Member,
            "admin" to MemberRole.Admin,
            "moderator" to MemberRole.Moderator
        )

        values.forEach { (string, expected) ->
            val returned = mapRole(string)
            Assertions.assertEquals(expected, returned)
        }
    }

    @Test
    @DisplayName("Mapping strings to member role throws if an invalid string was passed")
    fun mapRoleThrowsInvalidString() {
        val values = listOf("administrator", "member ", "memmber", "kdjd", "")

        values.forEach { string ->
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                mapRole(string)
            }
        }
    }

}