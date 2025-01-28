package me.bumiller.mol.feature.dashboard.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/**
 * Navigation route for the dashboard screen.
 */
@Serializable
data object DashboardScreen

/**
 * Extension function that adds a nav destination for the [DashboardScreen] route.
 */
fun NavGraphBuilder.dashboard() {
    composable<DashboardScreen> {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Dashboard")
        }
    }
}