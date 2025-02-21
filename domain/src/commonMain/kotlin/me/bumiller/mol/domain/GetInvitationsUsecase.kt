package me.bumiller.mol.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.data.repository.SimpleRepository
import me.bumiller.mol.domain.base.FlowUsecase
import me.bumiller.mol.model.law.InvitationStatus
import me.bumiller.mol.model.law.LawBookInvitation
import me.bumiller.mol.model.law.MemberRole
import me.bumiller.mol.model.range.InstantRange
import me.bumiller.mol.model.sort.SortConfig

/**
 * Usecase to get a collection of invitations.
 */
class GetInvitationsUsecase(
    private val invitationsRepository: SimpleRepository<Long, LawBookInvitation>
) : FlowUsecase<GetInvitationsUsecase.Query, List<LawBookInvitation>> {

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

    override fun invoke(input: Query): Flow<List<LawBookInvitation>> =
        invitationsRepository.getAll()
            .map { it.applyQuery(input) }

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