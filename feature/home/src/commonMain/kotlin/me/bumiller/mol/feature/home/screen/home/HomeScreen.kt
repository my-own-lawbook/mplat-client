package me.bumiller.mol.feature.home.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import me.bumiller.mol.feature.dashboard.navigation.DashboardScreen
import me.bumiller.mol.feature.dashboard.navigation.dashboard
import me.bumiller.mol.feature.home.HomeSection
import me.bumiller.mol.feature.profile.navigation.profile

/**
 * The composable for the home screen.
 */
@Composable
internal fun HomeScreen(

) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            val selected = backStackEntry.toRouteSafe()

            HomeSection.entries.forEach {
                it.asItem(
                    scope = this,
                    selected = it == selected,
                    onClick = { navController.navigate(it.route) }
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeNavHost(navController)
        }
    }
}

private fun NavBackStackEntry?.toRouteSafe(): Any? = this?.let {
    HomeSection.entries.forEach {
        if (destination.route == it.route::class.qualifiedName) {
            return it
        }
    }

    return null
}

@Composable
private fun HomeNavHost(
    controller: NavHostController
) {
    NavHost(
        navController = controller,
        startDestination = DashboardScreen
    ) {
        dashboard()
        profile()
    }
}