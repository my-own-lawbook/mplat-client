package me.bumiller.mol.app

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import me.bumiller.mol.feature.onboarding.navigation.OnboardingLocation
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
    val locationState by viewModel.topLevelLocation.collectAsStateWithLifecycle()

    val settings = settingsState.dataOrNull()
    val location = locationState.dataOrNull()

    if (settings != null && location != null) {
        MolTheme(
            windowSizeClass = windowSizeClass,
            colorMode = settings.colorMode,
            colorScheme = settings.colorScheme,
            contrastLevel = settings.contrastLevel
        ) {
            when (location) {
                MolTopLevelLocation.Onboarding -> OnboardingLocation {}
                else -> {}
            }
        }
    }
}