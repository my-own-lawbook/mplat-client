package me.bumiller.mol.feature.about.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import me.bumiller.mol.feature.about.screen.notice.NoticeScreen

/**
 * Navigation destination for the notice screen.
 */
@Serializable
data object NoticeScreen

/**
 * Top level composable for the about location
 */
fun NavGraphBuilder.aboutScreens() {
    composable<NoticeScreen> {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NoticeScreen()
        }
    }
}
