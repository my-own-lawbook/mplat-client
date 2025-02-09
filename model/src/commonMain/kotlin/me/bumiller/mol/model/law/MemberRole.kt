package me.bumiller.mol.model.law

/**
 * Models a role that a member of a law book can have.
 */
enum class MemberRole {

    /**
     * The user is a simple member, and can only do read operations.
     */
    Member,

    /**
     * The user is a moderator, and has some write privileges.
     */
    Moderator,

    /**
     * The user is an admin, and has all write privileges.
     */
    Admin

}