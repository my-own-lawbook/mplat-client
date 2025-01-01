package me.bumiller.mol.feature.onboarding.screen.url

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * The onboarding screen that allows the user to enter/change the url of the backend they are connecting to.
 *
 * @param modifier The modifier
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun UrlScreen() {
    val viewModel = koinViewModel<UrlViewModel>()
    val clocks by viewModel.counterState.collectAsStateWithLifecycle()

    Button(
        onClick = { viewModel.incCounter() }
    ) {
        Text("Clocks: $clocks")
    }
}