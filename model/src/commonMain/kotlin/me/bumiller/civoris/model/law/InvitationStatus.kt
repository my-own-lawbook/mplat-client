package me.bumiller.civoris.model.law

/**
 * Models the different statuses that a [LawBookInvitation] can be in.
 */
enum class InvitationStatus {

    /**
     * The invitation is still open.
     */
    Open,

    /**
     * The invitation has been accepted.
     */
    Accepted,

    /**
     * The invitation has been denied.
     */
    Declined,

    /**
     * The invitation was revoked.
     */
    Revoked

}