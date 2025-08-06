package me.bumiller.civoris.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import me.bumiller.civoris.feature.home.screen.home.HomeScreen

/**
 * Route for the home screen.
 */
@Serializable
data object HomeLocation

/**
 * Method to add a nav destination for [HomeLocation] to the nav graph.
 */
fun NavGraphBuilder.homeLocation(
    onGoToAbout: () -> Unit,
    onGoToAuth: () -> Unit
) {
    composable<HomeLocation> {
        HomeScreen(
            onGoToAbout = onGoToAbout,
            onGoToAuth = onGoToAuth
        )
    }
}