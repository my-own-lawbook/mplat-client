package me.bumiller.mol.feature.onboarding.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.bumiller.mol.feature.onboarding.screen.design.DesignScreen
import me.bumiller.mol.feature.onboarding.screen.url.UrlScreen

/**
 * Navigation destination for the url-screen.
 */
@Serializable
internal object UrlScreen

/**
 * Navigation destination for the design-screen.
 */
@Serializable
internal object DesignScreen

/**
 * Top level composable for the onboarding location.
 *
 * @param onOnboardingFinished The callback invoked when the onboarding procedure is finished
 */
@Composable
fun OnboardingLocation(
    onOnboardingFinished: () -> Unit
) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = UrlScreen
        ) {
            urlScreen(
                onUrlSet = { navController.navigate(DesignScreen) }
            )

            designScreen(
                onFinish = onOnboardingFinished,
                onBack = { navController.navigate(UrlScreen) }
            )
        }
    }
}

/**
 * Builds the design-screen-destination inside a nav-graph-builder.
 *
 * @param onFinish Callback when the user pressed the finish button
 * @param onBack Callback when the user clicked the back button
 */
internal fun NavGraphBuilder.designScreen(
    onFinish: () -> Unit,
    onBack: () -> Unit
) {
    composable<DesignScreen> {
        DesignScreen(onFinish, onBack)
    }
}

/**
 * Builds the url-screen-destination inside a nav-graph-builder.
 *
 * @param onUrlSet The callback invoked when the url has been successfully set.
 */
internal fun NavGraphBuilder.urlScreen(
    onUrlSet: () -> Unit
) {
    composable<UrlScreen> {
        UrlScreen(onUrlSet)
    }
}