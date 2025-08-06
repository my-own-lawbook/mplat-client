package me.bumiller.mol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eygraber.uri.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import me.bumiller.mol.app.CivorisAppRoot
import me.bumiller.mol.common.ui.LocalNavGraphSetupState
import me.bumiller.mol.common.ui.nav.CivorisDeepLink

/**
 * Main entrypoint into the android app.
 */
class MainActivity : ComponentActivity() {

    private val deepLinkFlow = MutableStateFlow<CivorisDeepLink?>(null)
    private val navGraphSetupFlow = MutableStateFlow(false)

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var showSplashScreen by mutableStateOf(true)

        splashScreen.setKeepOnScreenCondition { showSplashScreen }

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        deepLinkFlow.value = parseDeepLinkFromIntent(intent)

        setContent {
            val navGraphSetup by navGraphSetupFlow.collectAsStateWithLifecycle()
            val deepLink by deepLinkFlow.collectAsStateWithLifecycle()
            CompositionLocalProvider(
                LocalNavGraphSetupState provides navGraphSetup
            ) {
                CivorisAppRoot(
                    windowSizeClass = calculateWindowSizeClass(this@MainActivity),
                    onScreenReady = {
                        showSplashScreen = false
                    },
                    deepLink = deepLink
                )
            }
        }
        navGraphSetupFlow.value = true
    }

    private fun parseDeepLinkFromIntent(intent: Intent) = if (Intent.ACTION_VIEW == intent.action) {
        intent.data?.let { CivorisDeepLink.fromUri(Uri.parse(it.toString())) }
    } else null

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        deepLinkFlow.value = parseDeepLinkFromIntent(intent)
    }
}