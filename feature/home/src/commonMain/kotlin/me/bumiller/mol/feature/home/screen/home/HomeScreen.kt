package me.bumiller.mol.feature.home.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.snackbar.confirmedSnackbarMessage
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.feature.dashboard.navigation.DashboardScreen
import me.bumiller.mol.feature.dashboard.navigation.dashboard
import me.bumiller.mol.feature.home.HomeSection
import me.bumiller.mol.feature.profile.navigation.profile
import me.bumiller.mol.home.Res
import me.bumiller.mol.home.sync_failed_snackbar_action
import me.bumiller.mol.home.sync_failed_snackbar_message
import org.jetbrains.compose.resources.stringResource
import kotlin.system.exitProcess

/**
 * The composable for the home screen.
 */
@Composable
internal fun HomeScreen(
    onGoToAbout: () -> Unit,
    onGoToAuth: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val syncFailedMessage = stringResource(Res.string.sync_failed_snackbar_message)
    val syncFailedAction = stringResource(Res.string.sync_failed_snackbar_action)

    ViewModelScope<UiEvent, HomeEvent, HomeViewmodel>(
        onViewModelEvent = {
            when (it) {
                HomeEvent.SyncFailed -> {
                    snackbarHostState.confirmedSnackbarMessage(
                        syncFailedMessage,
                        syncFailedAction,
                        SnackbarDuration.Long
                    ) {
                        exitProcess(0)
                    }
                }
            }
        }
    ) {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
            null
        )

        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) {
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
                    HomeNavHost(
                        controller = navController,
                        onGoToAbout = onGoToAbout,
                        onGoToAuth = onGoToAuth
                    )
                }
            }
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
    controller: NavHostController,
    onGoToAbout: () -> Unit,
    onGoToAuth: () -> Unit
) {
    NavHost(
        navController = controller,
        startDestination = DashboardScreen
    ) {
        dashboard(
            onGoToAbout = onGoToAbout,
            onGoToAuth = onGoToAuth
        )
        profile()
    }
}