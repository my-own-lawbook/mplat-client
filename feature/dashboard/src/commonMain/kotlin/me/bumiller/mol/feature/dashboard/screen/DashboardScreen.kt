package me.bumiller.mol.feature.dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
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
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.dashboard.Res
import me.bumiller.mol.dashboard.cd_menu_action
import me.bumiller.mol.dashboard.cd_sync_action
import me.bumiller.mol.dashboard.dashboard_books_title
import me.bumiller.mol.dashboard.dashboard_invitations_title
import me.bumiller.mol.dashboard.dashboard_title
import me.bumiller.mol.dashboard.menu_about_label
import me.bumiller.mol.model.aggregate.LawBookInvitationAggregate
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.ui.components.TopAppBarStyles
import me.bumiller.mol.ui.feed.BookCardFeed
import me.bumiller.mol.ui.feed.InvitationList
import me.bumiller.mol.ui.layout.AppBarLayout
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DashboardScreen(
    onGoToBookDetail: (LawBook) -> Unit,
    onGoToInvitationDetail: (LawBookInvitationAggregate) -> Unit,
    onOpenAddBookDialog: () -> Unit,
    onGoToAbout: () -> Unit
) {
    ViewModelScope<DashboardUiEvent, DashboardEvent, DashboardViewmodel>(
        onViewModelEvent = {
            when (it) {
                is DashboardEvent.GoToBookDetail -> onGoToBookDetail(it.book)
                is DashboardEvent.GoToInvitationDetail -> onGoToInvitationDetail(it.invitation)
                DashboardEvent.ShowAddBookDialog -> onOpenAddBookDialog()
                DashboardEvent.GoToAbout -> onGoToAbout()
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