package me.bumiller.mol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var showSplashScreen by mutableStateOf(true)

        splashScreen.setKeepOnScreenCondition { showSplashScreen }

        setContent {
            App(
                onScreenReady = {
                    showSplashScreen = false
                }
            )
        }
    }
}