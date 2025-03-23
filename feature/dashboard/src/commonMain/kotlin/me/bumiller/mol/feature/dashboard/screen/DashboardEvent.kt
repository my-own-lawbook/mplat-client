package me.bumiller.mol.feature.dashboard.screen

import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent
import me.bumiller.mol.model.aggregate.LawBookInvitationAggregate
import me.bumiller.mol.model.law.LawBook


/**
 * Events sent from the dashboard ui to the view model
 */
internal sealed interface DashboardUiEvent : UiEvent {

    /**
     * User clicked a book in the list.
     *
     * @param book The book
     */
    data class ClickBook(val book: LawBook) : DashboardUiEvent

    /**
     * User clicked an invitation in the list.
     *
     * @param invitation The invitation
     */
    data class ClickInvitation(val invitation: LawBookInvitationAggregate) : DashboardUiEvent

    /**
     * User clicked on the button for creating a new book
     */
    data object ClickAddBook : DashboardUiEvent

    /**
     * User clicked on the menu action.
     */
    data object ClickMenu : DashboardUiEvent

    /**
     * User clicked on the about menu item.
     */
    data object ClickAbout : DashboardUiEvent

    /**
     * User clicked on the sync action.
     */
    data object ClickSync : DashboardUiEvent

    /**
     * The user clicked on the logout menu item.
     */
    data object Logout : DashboardUiEvent

}

/**
 * Events sent from the dashboard view model.
 */
internal sealed interface DashboardEvent : ViewModelEvent {

    /**
     * The dialog for the add book action should be opened.
     */
    data object ShowAddBookDialog : DashboardEvent

    /**
     * The user should be navigated to the about screen.
     */
    data object GoToAbout : DashboardEvent

    /**
     * The user should be navigated back to the auth screen, usually after logging out.
     */
    data object GoToAuth : DashboardEvent

    /**
     * The app should navigate to the book detail screen.
     *
     * @param book The book to be shown
     */
    data class GoToBookDetail(val book: LawBook) : DashboardEvent

    /**
     * The ap should navigate to the invitation detail screen.
     *
     * @param invitation The invitation to show
     */
    data class GoToInvitationDetail(val invitation: LawBookInvitationAggregate) : DashboardEvent

}
