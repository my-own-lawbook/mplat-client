package me.bumiller.civoris.app

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import me.bumiller.civoris.common.ui.LocalNavGraphSetupState
import me.bumiller.civoris.common.ui.nav.CivorisDeepLink
import me.bumiller.civoris.common.ui.nav.CivorisNavHost
import me.bumiller.civoris.common.ui.nav.CivorisTopLevelLocation
import me.bumiller.civoris.common.ui.nav.CivorisTopLevelLocation.Auth
import me.bumiller.civoris.common.ui.nav.CivorisTopLevelLocation.Home
import me.bumiller.civoris.common.ui.nav.CivorisTopLevelLocation.Onboarding
import me.bumiller.civoris.common.ui.nav.CivorisTopLevelLocation.Setting
import me.bumiller.civoris.feature.about.navigation.AboutLocation
import me.bumiller.civoris.feature.about.navigation.aboutScreens
import me.bumiller.civoris.feature.auth.navigation.AuthLocation
import me.bumiller.civoris.feature.auth.navigation.authLocation
import me.bumiller.civoris.feature.home.navigation.HomeLocation
import me.bumiller.civoris.feature.home.navigation.homeLocation
import me.bumiller.civoris.feature.onboarding.navigation.OnboardingLocation
import me.bumiller.civoris.feature.onboarding.navigation.onboardingLocation
import me.bumiller.civoris.ui.theme.CivorisTheme
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Root composable that wraps the entirety of the app.
 *
 * @param onScreenReady The callback invoked when the app has finished the initial loading procedures
 * @param deepLink The deep link with which the app was opened
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun CivorisAppRoot(
    windowSizeClass: WindowSizeClass,
    deepLink: CivorisDeepLink? = null,
    onScreenReady: () -> Unit = {}
) = KoinContext {
    val viewModel = koinViewModel<CivorisAppViewModel>()
    val navController = rememberNavController()
    val navGraphSetupBefore = LocalNavGraphSetupState.current

    // Notify the parent when this screen is ready.
    // May be used for a loading screen or the like.
    LaunchedEffect(Unit) {
        viewModel.settings.combine(viewModel.topLevelLocation) { settings, location ->
            settings.isSuccess && location.isSuccess
        }.collectLatest {
            if (it) {
                onScreenReady()
            }
        }
    }

    val settingsState by viewModel.settings.collectAsStateWithLifecycle()
    val initialLocationState by viewModel.topLevelLocation.collectAsStateWithLifecycle()

    val settings = settingsState.dataOrNull()
    val location = initialLocationState.dataOrNull()
    val deepLinkLocation = deepLink?.initialTopLevelLocation()

    if (settings != null && location != null) {
        CivorisTheme(
            windowSizeClass = windowSizeClass,
            colorMode = settings.colorMode,
            colorScheme = settings.colorScheme,
            contrastLevel = settings.contrastLevel
        ) {
            val initialLocation =
                decideInitialLocation(location, deepLinkLocation, navGraphSetupBefore)
            CivorisAppRootNavHost(
                navController = navController,
                initialLocation = initialLocation,
                deepLink = deepLink
            )
        }
    }
}

private fun decideInitialLocation(
    location: CivorisTopLevelLocation,
    deepLinkLocation: CivorisTopLevelLocation?,
    navGraphSetupBefore: Boolean
): CivorisTopLevelLocation = if (deepLinkLocation == null || navGraphSetupBefore) location else {
    if (!location.requiresAuth() && deepLinkLocation.requiresAuth()) location
    else deepLinkLocation
}

/**
 * Converts this [CivorisTopLevelLocation] to the associated nav route.
 */
private val CivorisTopLevelLocation.asNavRoute: Any
    get() = when (this) {
        Auth -> AuthLocation
        is Onboarding -> OnboardingLocation(showDesignScreen)
        Home -> HomeLocation
        Setting -> TODO()
    }

@Composable
private fun CivorisAppRootNavHost(
    navController: NavHostController,
    initialLocation: CivorisTopLevelLocation,
    deepLink: CivorisDeepLink?
) {
    CivorisNavHost(
        navController = navController,
        startDestination = initialLocation.asNavRoute,
        deepLink = deepLink,
        destinationForDeepLink = {
            it.initialTopLevelLocation().asNavRoute
        }
    ) {
        aboutScreens()
        homeLocation(
            onGoToAbout = { navController.navigate(AboutLocation) },
            onGoToAuth = {
                navController.navigate(AuthLocation) {
                    popUpTo<HomeLocation> {
                        inclusive = true
                    }
                }
            }
        )
        onboardingLocation(
            onOnboardingFinished = {
                navController.navigate(AuthLocation) {
                    popUpTo(OnboardingLocation::class) {
                        inclusive = true
                    }
                }
            }
        )
        authLocation(
            onUrlChange = {
                navController.navigate(OnboardingLocation(false)) {
                    popUpTo(AuthLocation) {
                        inclusive = true
                    }
                }
            },
            onAuthenticate = {
                navController.navigate(HomeLocation)
            },
            deepLink = deepLink
        )
    }
}