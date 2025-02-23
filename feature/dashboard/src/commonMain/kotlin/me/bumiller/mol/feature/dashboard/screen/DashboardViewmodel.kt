package me.bumiller.mol.feature.dashboard.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.domain.GetBooksUsecase
import me.bumiller.mol.domain.GetInvitationsUsecase
import me.bumiller.mol.model.law.InvitationStatus
import me.bumiller.mol.model.sort.SortConfig
import me.bumiller.mol.model.sort.SortDirection
import me.bumiller.mol.model.sort.SortMode
import me.bumiller.mol.model.state.SimpleState

/**
 * Viewmodel for the dashboard screen.
 */
internal class DashboardViewmodel(
    private val getBooks: GetBooksUsecase,
    private val getInvitations: GetInvitationsUsecase
) : MolViewModel<DashboardUiEvent, DashboardEvent>() {

    /**
     * The state flow containing the state of the view model.
     */
    val state = createState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardState()
        )

    override suspend fun handleEvent(event: DashboardUiEvent) = when (event) {
        DashboardUiEvent.ClickAddBook -> fireEvent(DashboardEvent.ShowAddBookDialog)
        is DashboardUiEvent.ClickBook -> fireEvent(DashboardEvent.GoToBookDetail(event.book))
        is DashboardUiEvent.ClickInvitation -> fireEvent(DashboardEvent.GoToInvitationDetail(event.invitation))
    }

    private fun createState(): Flow<DashboardState> {
        val booksQuery = GetBooksUsecase.Query(favoritesToBeginning = true)
        val invitationsQuery = GetInvitationsUsecase.Query(
            sortConfig = SortConfig(direction = SortDirection.Descending, SortMode.Created),
            statuses = setOf(InvitationStatus.Open)
        )

        val booksFlow = getBooks(booksQuery)
        val invitationsFlow = getInvitations(invitationsQuery)

        return combine(booksFlow, invitationsFlow) { books, invitations ->
            DashboardState(
                booksState = SimpleState.success(books),
                invitationsState = SimpleState.success(invitations)
            )
        }
    }

}