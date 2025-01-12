package me.bumiller.mol.app

import kotlinx.serialization.Serializable
import me.bumiller.mol.feature.auth.navigation.AuthLocation
import me.bumiller.mol.feature.onboarding.navigation.OnboardingLocation

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
     * Converts this [MolTopLevelLocation] to the associated nav route.
     */
    val asNavRoute: Any
        get() = when (this) {
            Auth -> AuthLocation
            is Onboarding -> OnboardingLocation(showDesignScreen)
            Home -> TODO()
            Setting -> TODO()
        }

}