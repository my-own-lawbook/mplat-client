package me.bumiller.mol.common.ui.nav

import kotlinx.serialization.Serializable

/**
 * Locations that the app root may directly embed.
 */
@Serializable
sealed interface CivorisTopLevelLocation {

    /**
     * The onboarding route.
     */
    @Serializable
    data class Onboarding(

        /**
         * Whether the design screen should be shown
         */
        val showDesignScreen: Boolean = true

    ) : CivorisTopLevelLocation

    /**
     * The home route, main part of the app.
     */
    @Serializable
    data object Home : CivorisTopLevelLocation

    /**
     * The auth route for logging in/ signing up.
     */
    @Serializable
    data object Auth : CivorisTopLevelLocation

    /**
     * The settings route.
     */
    @Serializable
    data object Setting : CivorisTopLevelLocation

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