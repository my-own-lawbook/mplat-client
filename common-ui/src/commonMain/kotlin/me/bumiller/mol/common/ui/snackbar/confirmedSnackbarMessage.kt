package me.bumiller.mol.common.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

/**
 * Shows a snackbar.
 *
 * @param message The message to show
 * @param actionLabel The action label of the snackbar
 * @param duration The duration of the snackbar
 * @param onConfirmed The callback called when the action is clicked
 */
suspend fun SnackbarHostState.confirmedSnackbarMessage(
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration,
    onConfirmed: suspend () -> Unit
) {
    val returned = showSnackbar(message, actionLabel, true, duration)

    if (returned == SnackbarResult.ActionPerformed) {
        onConfirmed()
    }
}