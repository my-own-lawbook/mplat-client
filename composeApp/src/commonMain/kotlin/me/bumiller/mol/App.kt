package me.bumiller.mol

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
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
fun App(
    onScreenReady: () -> Unit = {}
) = KoinContext {
    val viewModel = koinViewModel<MolAppViewModel>()

    // Notify the parent when this screen is ready.
    // May be used for a loading screen or the like.
    LaunchedEffect(Unit) {
        viewModel.stats.collectLatest {
            if (it.isSuccess) {
                onScreenReady()
            }
        }
    }

    val settingsStateFlow by viewModel.stats.collectAsStateWithLifecycle()

    Text(settingsStateFlow.toString())
}