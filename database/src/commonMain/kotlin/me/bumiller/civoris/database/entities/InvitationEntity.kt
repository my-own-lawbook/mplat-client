package me.bumiller.civoris.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import me.bumiller.civoris.database.entities.base.SimpleEntity

/**
 * Entity that resembles a record in the 'invitations' table.
 */
@Entity(
    tableName = "invitations"
)
data class InvitationEntity(

    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    /**
     * The id of the author [ForeignUserEntity] that sent the invitation.
     */
    val authorId: Long,

    /**
     * The id of tha recipient [ForeignUserEntity] that received the invitation.
     */
    val recipientId: Long,

    /**
     * The id of the [BookEntity] that the invitation is targeted towards.
     */
    val targetId: Long,

    /**
     * The role that comes with accepting the invitation. One of the following:
     * - admin
     * - moderator
     * - member
     */
    val role: String,

    /**
     * The timestamp at which the invitation was sent.
     */
    val sentTimestamp: Instant,

    /**
     * The timestamp at which the invitation was used (i.e. accepted, denied or revoked), or null if it is still open.
     */
    val usedTimestamp: Instant?,

    /**
     * The timestamp at which the invitation has/will expired(d), or null if the invitation will never expire.
     */
    val expiredTimestamp: Instant?,

    /**
     * The current status of the invitation. One of the following:
     * - open
     * - accepted
     * - declined
     * - revoked
     */
    val status: String,

    /**
     * The message, or null if none was included.
     */
    val message: String?


) : SimpleEntity
