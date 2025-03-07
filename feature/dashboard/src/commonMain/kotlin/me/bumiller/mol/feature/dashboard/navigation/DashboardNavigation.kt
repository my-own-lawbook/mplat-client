package me.bumiller.mol.feature.dashboard.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import me.bumiller.mol.feature.dashboard.screen.DashboardScreen

/**
 * Navigation route for the dashboard screen.
 */
@Serializable
data object DashboardScreen

/**
 * Extension function that adds a nav destination for the [DashboardScreen] route.
 */
fun NavGraphBuilder.dashboard(
    onGoToAbout: () -> Unit
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
                onGoToAbout = onGoToAbout
            )
        }
    }
}