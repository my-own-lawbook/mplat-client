package me.bumiller.mol.app

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import me.bumiller.mol.common.ui.LocalNavGraphSetupState
import me.bumiller.mol.common.ui.nav.CivorisDeepLink
import me.bumiller.mol.common.ui.nav.CivorisNavHost
import me.bumiller.mol.common.ui.nav.MolTopLevelLocation
import me.bumiller.mol.common.ui.nav.MolTopLevelLocation.Auth
import me.bumiller.mol.common.ui.nav.MolTopLevelLocation.Home
import me.bumiller.mol.common.ui.nav.MolTopLevelLocation.Onboarding
import me.bumiller.mol.common.ui.nav.MolTopLevelLocation.Setting
import me.bumiller.mol.feature.about.navigation.AboutLocation
import me.bumiller.mol.feature.about.navigation.aboutScreens
import me.bumiller.mol.feature.auth.navigation.AuthLocation
import me.bumiller.mol.feature.auth.navigation.authLocation
import me.bumiller.mol.feature.home.navigation.HomeLocation
import me.bumiller.mol.feature.home.navigation.homeLocation
import me.bumiller.mol.feature.onboarding.navigation.OnboardingLocation
import me.bumiller.mol.feature.onboarding.navigation.onboardingLocation
import me.bumiller.mol.ui.theme.MolTheme
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
internal fun MolAppRoot(
    windowSizeClass: WindowSizeClass,
    deepLink: CivorisDeepLink? = null,
    onScreenReady: () -> Unit = {}
) = KoinContext {
    val viewModel = koinViewModel<MolAppViewModel>()
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
        MolTheme(
            windowSizeClass = windowSizeClass,
            colorMode = settings.colorMode,
            colorScheme = settings.colorScheme,
            contrastLevel = settings.contrastLevel
        ) {
            val initialLocation =
                decideInitialLocation(location, deepLinkLocation, navGraphSetupBefore)
            MolAppRootNavHost(
                navController = navController,
                initialLocation = initialLocation,
                deepLink = deepLink
            )
        }
    }
}

private fun decideInitialLocation(
    location: MolTopLevelLocation,
    deepLinkLocation: MolTopLevelLocation?,
    navGraphSetupBefore: Boolean
): MolTopLevelLocation = if (deepLinkLocation == null || navGraphSetupBefore) location else {
    if (!location.requiresAuth() && deepLinkLocation.requiresAuth()) location
    else deepLinkLocation
}

/**
 * Converts this [MolTopLevelLocation] to the associated nav route.
 */
private val MolTopLevelLocation.asNavRoute: Any
    get() = when (this) {
        Auth -> AuthLocation
        is Onboarding -> OnboardingLocation(showDesignScreen)
        Home -> HomeLocation
        Setting -> TODO()
    }

@Composable
private fun MolAppRootNavHost(
    navController: NavHostController,
    initialLocation: MolTopLevelLocation,
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