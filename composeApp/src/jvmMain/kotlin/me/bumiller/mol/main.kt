package me.bumiller.mol

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.bumiller.mol.di.initKoin

/**
 * Main entrypoint into the desktop app
 */
fun main() {
    initKoin()

    application {
        var showContent by remember {
            mutableStateOf(false)
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "mplat-client",
            visible = showContent
        ) {
            App(
                onScreenReady = {
                    showContent = true
                }
            )
        }
    }
}