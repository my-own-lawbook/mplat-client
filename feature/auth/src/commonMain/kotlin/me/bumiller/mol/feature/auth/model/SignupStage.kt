package me.bumiller.mol.feature.auth.model

import me.bumiller.mol.model.user.AuthUser
import me.bumiller.mol.model.user.AuthUserWithProfile

/**
 * Describes the stage that a user can be in the signup process.
 */
enum class SignupStage {

    /**
     * The user has not yet started with the signup process.
     *
     * The next step is creating an account.
     */
    NotStarted,

    /**
     * The user has created an account.
     *
     * The next step is to verify the email address.
     */
    AccountCreated,

    /**
     * The user has verified the email address.
     *
     * The next step is to finish their profile.
     */
    EmailVerified,

    /**
     * The user has set up their profile.
     *
     * The user is fully signed up.
     */
    Finished;

    /**
     * Gets the next signup step.
     *
     * @return The step after the current step, or null if the user is finished.
     */
    fun next() = when (this) {
        NotStarted -> AccountCreated
        AccountCreated -> EmailVerified
        EmailVerified -> Finished
        Finished -> null
    }

    companion object {

        /**
         * Creates the current [SignupStage] from a given user account.
         *
         * @param user The user object to check for the signup stage
         * @return The current stage of the user
         */
        fun fromUser(user: AuthUser?) = when (user) {
            null -> NotStarted
            is AuthUserWithProfile -> Finished
            else -> if (user.isEmailVerified) EmailVerified else AccountCreated
        }

    }

}