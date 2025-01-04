package me.bumiller.mol.feature.auth.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.bumiller.mol.feature.auth.screen.welcome.WelcomeScreen

/**
 * Navigation destination for the welcome screen.
 */
@Serializable
data object WelcomeScreen

/**
 * Root composable for the auth location.
 */
@Composable
fun AuthLocation(

) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = WelcomeScreen
        ) {
            welcomeScreen(
                onLogin = {},
                onSignup = {}
            )
        }
    }
}

/**
 * Builds the welcome-screen-destination inside a nav-graph-builder.
 *
 * @param onLogin Callback when the user chose to login
 * @param onSignup Callback when the user chose to signup
 */
internal fun NavGraphBuilder.welcomeScreen(
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {
    composable<WelcomeScreen> {
        WelcomeScreen(onLogin, onSignup)
    }
}