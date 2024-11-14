package me.bumiller.mol

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.bumiller.mol.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "mplat-client",
        ) {
            App()
        }
    }
}