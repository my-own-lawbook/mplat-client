package me.bumiller.civoris.feature.about.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.bumiller.civoris.feature.about.screen.about.AboutScreen
import me.bumiller.civoris.feature.about.screen.notice.NoticeScreen

/**
 * Navigation destination for the notice location.
 */
@Serializable
data object AboutLocation

/**
 * Navigation destination for the notice screen.
 */
@Serializable
internal data object NoticeScreen

/**
 * Navigation destination for the about screen.
 */
@Serializable
internal data object AboutScreen

/**
 * Top level composable for the about location
 */
fun NavGraphBuilder.aboutScreens() = composable<AboutLocation> {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AboutScreen
    ) {
        composable<NoticeScreen> {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NoticeScreen()
            }
        }
        composable<AboutScreen> {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AboutScreen(
                    onShowNotice = {
                        navController.navigate(NoticeScreen)
                    }
                )
            }
        }
    }
}
