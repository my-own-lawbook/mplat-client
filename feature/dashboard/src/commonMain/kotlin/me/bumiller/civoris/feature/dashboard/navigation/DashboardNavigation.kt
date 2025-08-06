package me.bumiller.civoris.feature.dashboard.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import me.bumiller.civoris.feature.dashboard.screen.DashboardScreen

/**
 * Navigation route for the dashboard screen.
 */
@Serializable
data object DashboardScreen

/**
 * Extension function that adds a nav destination for the [DashboardScreen] route.
 *
 * @param onGoToAbout Callback called when the user should be redirected to the about screen
 * @param onGoToAuth Callback called when the user should be redirected to the auth screen
 */
fun NavGraphBuilder.dashboard(
    onGoToAbout: () -> Unit,
    onGoToAuth: () -> Unit
) {
    composable<DashboardScreen> {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            DashboardScreen(
                onGoToInvitationDetail = {},
                onGoToBookDetail = {},
                onOpenAddBookDialog = {},
                onGoToAbout = onGoToAbout,
                onGoToAuth = onGoToAuth
            )
        }
    }
}