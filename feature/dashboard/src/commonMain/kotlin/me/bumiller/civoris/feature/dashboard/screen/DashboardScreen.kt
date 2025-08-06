package me.bumiller.civoris.feature.dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.CloudSync
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.bumiller.civoris.common.ui.viewmodel.ViewModelScope
import me.bumiller.civoris.dashboard.Res
import me.bumiller.civoris.dashboard.cd_menu_action
import me.bumiller.civoris.dashboard.cd_sync_action
import me.bumiller.civoris.dashboard.dashboard_books_title
import me.bumiller.civoris.dashboard.dashboard_invitations_title
import me.bumiller.civoris.dashboard.dashboard_title
import me.bumiller.civoris.dashboard.menu_about_label
import me.bumiller.civoris.dashboard.menu_logout_label
import me.bumiller.civoris.model.aggregate.LawBookInvitationAggregate
import me.bumiller.civoris.model.law.LawBook
import me.bumiller.civoris.ui.components.TopAppBarStyles
import me.bumiller.civoris.ui.feed.BookCardFeed
import me.bumiller.civoris.ui.feed.InvitationList
import me.bumiller.civoris.ui.layout.AppBarLayout
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DashboardScreen(
    onGoToBookDetail: (LawBook) -> Unit,
    onGoToInvitationDetail: (LawBookInvitationAggregate) -> Unit,
    onOpenAddBookDialog: () -> Unit,
    onGoToAbout: () -> Unit,
    onGoToAuth: () -> Unit
) {
    ViewModelScope<DashboardUiEvent, DashboardEvent, DashboardViewmodel>(
        onViewModelEvent = {
            when (it) {
                is DashboardEvent.GoToBookDetail -> onGoToBookDetail(it.book)
                is DashboardEvent.GoToInvitationDetail -> onGoToInvitationDetail(it.invitation)
                DashboardEvent.ShowAddBookDialog -> onOpenAddBookDialog()
                DashboardEvent.GoToAbout -> onGoToAbout()
                DashboardEvent.GoToAuth -> onGoToAuth()
            }
        }
    ) { vm ->
        val state by vm.state.collectAsStateWithLifecycle()
        val uiState by vm.uiState.collectAsStateWithLifecycle()

        DashboardScreen(vm::onEvent, state, uiState)
    }
}

@Composable
private fun DashboardScreen(
    onEvent: (DashboardUiEvent) -> Unit,
    state: DashboardState,
    uiState: DashboardUiState
) {
    AppBarLayout(
        modifier = Modifier
            .fillMaxSize(),
        appBarStyle = TopAppBarStyles.Centered,
        title = {
            Text(
                text = stringResource(Res.string.dashboard_title)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    onEvent(DashboardUiEvent.ClickSync)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.CloudSync,
                    contentDescription = stringResource(Res.string.cd_sync_action)
                )
            }
            IconButton(
                onClick = {
                    onEvent(DashboardUiEvent.ClickMenu)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(Res.string.cd_menu_action)
                )
            }

            DropdownMenu(
                expanded = uiState.isMenuOpened,
                onDismissRequest = { onEvent(DashboardUiEvent.ClickMenu) }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(Res.string.menu_about_label))
                    },
                    onClick = { onEvent(DashboardUiEvent.ClickAbout) }
                )
                DropdownMenuItem(
                    text = {
                        Text(stringResource(Res.string.menu_logout_label))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = stringResource(Res.string.menu_logout_label)
                        )
                    },
                    onClick = { onEvent(DashboardUiEvent.Logout) }
                )
            }
        },
        firstContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.dashboard_books_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                BookCardFeed(
                    modifier = Modifier,
                    state = state.booksState,
                    onClick = { onEvent(DashboardUiEvent.ClickBook(it)) }
                )
            }
        },
        secondContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.dashboard_invitations_title),
                    style = MaterialTheme.typography.headlineLarge
                )
                InvitationList(
                    state = state.invitationsState,
                    onClick = { onEvent(DashboardUiEvent.ClickInvitation(it)) }
                )
            }
        }
    )
}