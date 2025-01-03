package me.bumiller.mol

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.bumiller.mol.app.MolAppRoot
import me.bumiller.mol.di.initKoin

/**
 * Main entrypoint into the desktop app
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
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
            MolAppRoot(
                windowSizeClass = calculateWindowSizeClass(),
                onScreenReady = {
                    showContent = true
                }
            )
        }
    }
}