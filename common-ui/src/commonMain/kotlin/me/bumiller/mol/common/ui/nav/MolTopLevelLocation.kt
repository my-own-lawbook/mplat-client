package me.bumiller.mol.common.ui.nav

import kotlinx.serialization.Serializable

/**
 * Locations that the app root may directly embed.
 */
@Serializable
sealed interface MolTopLevelLocation {

    /**
     * The onboarding route.
     */
    @Serializable
    data class Onboarding(

        /**
         * Whether the design screen should be shown
         */
        val showDesignScreen: Boolean = true

    ) : MolTopLevelLocation

    /**
     * The home route, main part of the app.
     */
    @Serializable
    data object Home : MolTopLevelLocation

    /**
     * The auth route for logging in/ signing up.
     */
    @Serializable
    data object Auth : MolTopLevelLocation

    /**
     * The settings route.
     */
    @Serializable
    data object Setting : MolTopLevelLocation

    /**
     * Checks whether the given location requires the user to be authenticated.
     *
     * @return Whether the user requires auth for the location
     */
    fun requiresAuth(): Boolean =
        when (this) {
            Auth -> false
            is Onboarding -> false
            Setting -> false
            Home -> true
        }

}