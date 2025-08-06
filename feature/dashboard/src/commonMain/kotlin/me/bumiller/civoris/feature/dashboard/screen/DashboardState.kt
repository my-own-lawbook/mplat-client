package me.bumiller.civoris.feature.dashboard.screen

import me.bumiller.civoris.model.aggregate.LawBookInvitationAggregate
import me.bumiller.civoris.model.law.LawBook
import me.bumiller.civoris.model.state.SimpleState

/**
 * State for the dashboard screen.
 */
internal data class DashboardState(

    /**
     * The feed state for the books list.
     */
    val booksState: SimpleState<List<LawBook>> = SimpleState.loading(),

    /**
     * The feed state for the invitations list.
     */
    val invitationsState: SimpleState<List<LawBookInvitationAggregate>> = SimpleState.loading()

)

/**
 * Ui state for the dashboard screen.
 */
internal data class DashboardUiState(

    /**
     * Whether the menu is currently opened
     */
    val isMenuOpened: Boolean = false

)
