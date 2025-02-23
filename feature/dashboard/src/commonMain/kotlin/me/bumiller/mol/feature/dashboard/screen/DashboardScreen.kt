package me.bumiller.mol.feature.dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.dashboard.Res
import me.bumiller.mol.dashboard.dashboard_books_title
import me.bumiller.mol.dashboard.dashboard_invitations_title
import me.bumiller.mol.dashboard.dashboard_title
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
    onOpenAddBookDialog: () -> Unit
) {
    ViewModelScope<DashboardUiEvent, DashboardEvent, DashboardViewmodel>(
        onViewModelEvent = {
            when (it) {
                is DashboardEvent.GoToBookDetail -> onGoToBookDetail(it.book)
                is DashboardEvent.GoToInvitationDetail -> onGoToInvitationDetail(it.invitation)
                DashboardEvent.ShowAddBookDialog -> onOpenAddBookDialog()
            }
        }
    ) { vm ->
        val state by vm.state.collectAsStateWithLifecycle()

        DashboardScreen(vm::onEvent, state)
    }
}

@Composable
private fun DashboardScreen(
    onEvent: (DashboardUiEvent) -> Unit,
    state: DashboardState
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