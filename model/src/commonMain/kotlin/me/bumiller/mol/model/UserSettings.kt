package me.bumiller.mol.model

/**
 * Contains the app-wide settings of the user
 */
data class UserSettings(

    /**
     * The color mode for the app
     */
    val colorMode: ColorMode,

    /**
     * The color scheme for the app
     */
    val colorScheme: ColorScheme,

    /**
     * The url to the backend.
     *
     * Sadly no kotlin multiplatform URL class exists yet, so we need to use a string
     */
    val backendUrl: String

)
