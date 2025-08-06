package me.bumiller.civoris.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import me.bumiller.civoris.data.repository.ForeignUserRepository
import me.bumiller.civoris.data.repository.LawBookInvitationRepository
import me.bumiller.civoris.data.repository.LawBookRepository
import me.bumiller.civoris.domain.base.FlowUsecase
import me.bumiller.civoris.domain.common.flatMapMergeCombine
import me.bumiller.civoris.model.aggregate.LawBookInvitationAggregate
import me.bumiller.civoris.model.law.InvitationStatus
import me.bumiller.civoris.model.law.LawBookInvitation
import me.bumiller.civoris.model.law.MemberRole
import me.bumiller.civoris.model.range.InstantRange
import me.bumiller.civoris.model.sort.SortConfig

/**
 * Usecase to get a collection of invitations.
 */
class GetInvitationsUsecase(
    private val invitationsRepository: LawBookInvitationRepository,
    private val userRepository: ForeignUserRepository,
    private val bookRepository: LawBookRepository
) : FlowUsecase<GetInvitationsUsecase.Query, List<LawBookInvitationAggregate>> {

    /**
     * The query for getting the invitations.
     */
    data class Query(

        /**
         * The sort config to sort the collection.
         */
        val sortConfig: SortConfig = SortConfig.None,

        /**
         * Including only invitations that match the given role
         */
        val roles: Set<MemberRole> = MemberRole.entries.toSet(),

        /**
         * The range of dates to include invitations by, based on their created timestamp
         */
        val dateRange: InstantRange? = null,

        /**
         * The invitation status to filter by.
         */
        val statuses: Set<InvitationStatus> = InvitationStatus.entries.toSet()

    )

    override fun invoke(input: Query): Flow<List<LawBookInvitationAggregate>> =
        invitationsRepository.getAll()
            .map { it.applyQuery(input) }
            .flatMapMergeCombine { invitation ->
                val authorFlow = userRepository.getById(invitation.authorId).filterNotNull()
                val targetFlow = bookRepository.getById(invitation.targetId).filterNotNull()

                combine(authorFlow, targetFlow) { author, target ->
                    LawBookInvitationAggregate(invitation, author, target)
                }
            }

    private fun List<LawBookInvitation>.applyQuery(query: Query) =
        filterRoles(query.roles)
            .filterStatuses(query.statuses)
            .run { query.dateRange?.let { filterDate(it) } ?: this }

    private fun List<LawBookInvitation>.filterRoles(roles: Set<MemberRole>) =
        filter { it.role in roles }

    private fun List<LawBookInvitation>.filterDate(dateRange: InstantRange) =
        filter { it.sentTimestamp in dateRange }

    private fun List<LawBookInvitation>.filterStatuses(statuses: Set<InvitationStatus>) =
        filter { it.status in statuses }

}