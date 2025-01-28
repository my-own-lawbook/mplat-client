package me.bumiller.mol.feature.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
import me.bumiller.mol.feature.dashboard.navigation.DashboardScreen
import me.bumiller.mol.feature.profile.navigation.ProfileScreen
import me.bumiller.mol.home.Res
import me.bumiller.mol.home.section_home_label
import me.bumiller.mol.home.section_profile_label
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * The sections the home screen allows a user to navigate to.
 */
@Serializable
internal enum class HomeSection(

    /**
     * The resource for the label.
     */
    private val labelResource: StringResource,

    /**
     * The icon to be shown on the navigation component.
     */
    private val icon: ImageVector,

    /**
     * The object associated with the nav destination.
     */
    val route: Any

) {

    /**
     * The dashboard screen giving the user a basic overview of the books and current invitations.
     */
    Dashboard(Res.string.section_home_label, Icons.Outlined.Home, DashboardScreen),

    /**
     * The profile screen.
     */
    Profile(Res.string.section_profile_label, Icons.Outlined.PersonOutline, ProfileScreen);

    /**
     * Calls the [NavigationSuiteScope.item] function with information about this section.
     *
     * @param selected Whether this is the selected section
     * @param onClick The onclick callback
     * @param badgeCount The count to show on the badge
     */
    fun asItem(
        scope: NavigationSuiteScope,
        selected: Boolean,
        onClick: () -> Unit,
        badgeCount: Int? = null
    ) {
        scope.item(
            selected = selected,
            label = { Text(stringResource(labelResource)) },
            onClick = onClick,
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(labelResource)
                )
            },
            badge = badgeCount?.let {
                { Text(badgeCount.toString()) }
            }
        )
    }

}