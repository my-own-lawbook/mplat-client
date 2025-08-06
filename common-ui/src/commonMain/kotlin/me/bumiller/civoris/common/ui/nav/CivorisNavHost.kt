package me.bumiller.civoris.common.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.bumiller.civoris.common.ui.LocalNavGraphSetupState

/**
 * [NavHost] wrapper that handles the deep link navigations.
 *
 * @param navController The navigation controller
 * @param startDestination The starting destination of the nav graph
 * @param deepLink The current deep link, or null
 * @param destinationForDeepLink Converts a given deep-link to a nav destination
 * @param builder The nav-graph builder
 */
@Composable
fun CivorisNavHost(
    navController: NavHostController,
    startDestination: Any,
    deepLink: CivorisDeepLink?,
    destinationForDeepLink: (CivorisDeepLink) -> Any?,
    builder: NavGraphBuilder.() -> Unit
) {
    val navGraphSetupBefore = LocalNavGraphSetupState.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = builder
    )

    LaunchedEffect(deepLink) {
        if (deepLink != null && navGraphSetupBefore) {
            val destination = destinationForDeepLink(deepLink)
            destination?.let(navController::navigate)
        }
    }
}