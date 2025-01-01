package me.bumiller.mol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import me.bumiller.mol.app.MolAppRoot

/**
 * Main entrypoint into the android app.
 */
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var showSplashScreen by mutableStateOf(true)

        splashScreen.setKeepOnScreenCondition { showSplashScreen }

        setContent {
            MolAppRoot(
                windowSizeClass = calculateWindowSizeClass(this@MainActivity),
                onScreenReady = {
                    showSplashScreen = false
                }
            )
        }
    }
}