package me.bumiller.mol.model

import com.eygraber.uri.Url

/**
 * Contains the app-wide settings of the user.
 */
data class UserSettings(

    /**
     * The color mode for the app.
     */
    val colorMode: ColorMode,

    /**
     * The color scheme for the app.
     */
    val colorScheme: ColorScheme,

    /**
     * The level of contrast for the color scheme.
     */
    val contrastLevel: ColorSchemeContrastLevel,

    /**
     * The url to the backend.
     */
    val backendUrl: Url?

)
