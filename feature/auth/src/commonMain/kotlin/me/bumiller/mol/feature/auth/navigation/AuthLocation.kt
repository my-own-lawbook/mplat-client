package me.bumiller.mol.feature.auth.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.bumiller.mol.feature.auth.screen.login.LoginScreen
import me.bumiller.mol.feature.auth.screen.welcome.WelcomeScreen

/**
 * Navigation destination for the auth location.
 */
@Serializable
data object AuthLocation

/**
 * Navigation destination for the welcome screen.
 */
@Serializable
internal data object WelcomeScreen

/**
 * Navigation destination for the login screen.
 */
@Serializable
internal data object LoginScreen

/**
 * Root composable for the auth location.
 *
 * @param onUrlChange Callback for when the user wants to change the url.
 * @param onAuthenticate Callback for when the user authenticated successfully.
 */
fun NavGraphBuilder.authLocation(
    onUrlChange: () -> Unit,
    onAuthenticate: () -> Unit
) = composable<AuthLocation> {
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
                onLogin = { navController.navigate(LoginScreen) },
                onUrlClick = onUrlChange,
                onSignup = {}
            )
            loginScreen(
                onBack = { navController.popBackStack() },
                onAuthenticate = onAuthenticate,
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
 * @param onUrlClick Callback when the wants to change the url.
 */
internal fun NavGraphBuilder.welcomeScreen(
    onLogin: () -> Unit,
    onSignup: () -> Unit,
    onUrlClick: () -> Unit
) {
    composable<WelcomeScreen> {
        WelcomeScreen(onLogin, onSignup, onUrlClick)
    }
}

/**
 * Builds the login-screen-destination inside a nav-graph-builder.
 *
 * @param onBack The callback invoked when the back button is clicked.
 * @param onSignup The callback invoked when the link to the signup screen is clicked.
 * @param onAuthenticate The callback invoked when the user successfully authenticated.
 */
internal fun NavGraphBuilder.loginScreen(
    onBack: () -> Unit,
    onSignup: () -> Unit,
    onAuthenticate: () -> Unit
) {
    composable<LoginScreen> {
        LoginScreen(onBack, onSignup, onAuthenticate)
    }
}