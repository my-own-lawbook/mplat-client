package me.bumiller.mol.feature.dashboard.screen

import me.bumiller.mol.model.aggregate.LawBookInvitationAggregate
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.state.SimpleState

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
