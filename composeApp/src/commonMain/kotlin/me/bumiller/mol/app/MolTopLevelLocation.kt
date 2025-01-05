package me.bumiller.mol.app

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
    data object Onboarding : MolTopLevelLocation

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

}