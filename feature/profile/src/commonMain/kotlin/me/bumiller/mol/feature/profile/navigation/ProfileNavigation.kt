package me.bumiller.mol.feature.profile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

/**
 * Navigation route for the profile screen.
 */
@Serializable
data object ProfileScreen

/**
 * Extension function that adds a nav destination for the [ProfileScreen] route.
 */
fun NavGraphBuilder.profile() {
    composable<ProfileScreen> {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Profile")
        }
    }
}