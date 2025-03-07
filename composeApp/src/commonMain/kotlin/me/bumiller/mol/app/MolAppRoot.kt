package me.bumiller.mol.app

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
fun MolAppRoot(
    windowSizeClass: WindowSizeClass,
    onScreenReady: () -> Unit = {}
) = KoinContext {
    val viewModel = koinViewModel<MolAppViewModel>()
    val navController = rememberNavController()

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

    if (settings != null && location != null) {
        MolTheme(
            windowSizeClass = windowSizeClass,
            colorMode = settings.colorMode,
            colorScheme = settings.colorScheme,
            contrastLevel = settings.contrastLevel
        ) {
            MolAppRootNavHost(
                navController = navController,
                initialLocation = location
            )
        }
    }
}

@Composable
private fun MolAppRootNavHost(
    navController: NavHostController,
    initialLocation: MolTopLevelLocation
) {
    NavHost(
        navController = navController,
        startDestination = initialLocation.asNavRoute
    ) {
        aboutScreens()
        homeLocation(
            onGoToAbout = { navController.navigate(AboutLocation) }
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
            }
        )
    }
}