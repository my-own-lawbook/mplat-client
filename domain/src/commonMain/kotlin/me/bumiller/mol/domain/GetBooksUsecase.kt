package me.bumiller.mol.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.data.repository.LawBookRepository
import me.bumiller.mol.domain.base.FlowUsecase
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.sort.SortConfig
import me.bumiller.mol.model.sort.sortWith

/**
 * Usecase to get a collection of books.
 */
class GetBooksUsecase(
    private val bookRepository: LawBookRepository
) : FlowUsecase<GetBooksUsecase.Query, List<LawBook>> {

    /**
     * The query for getting the books.
     */
    data class Query(

        /**
         * The config for sorting the books.
         */
        val sortConfig: SortConfig = SortConfig.None,

        /**
         * Whether the books marked as favorite should be put at the beginning.
         */
        val favoritesToBeginning: Boolean = false,

        /**
         * Whether only favourites should be returned.
         */
        val onlyFavorites: Boolean = false,

        /**
         * Whether only books should be included of which the user is a member of, and does not only have temporary access to.
         */
        val onlyMemberOf: Boolean = true

    )

    override fun invoke(input: Query): Flow<List<LawBook>> =
        bookRepository.getAll().map {
            it.applyQuery(input)
        }

    private fun List<LawBook>.applyQuery(query: Query) =
        filterOnlyFavorites(query.onlyFavorites)
            .filterOnlyMemberOf(query.onlyMemberOf)
            .sortWith(query.sortConfig)
            .splitFavorites(query.favoritesToBeginning)

    private fun List<LawBook>.splitFavorites(split: Boolean) =
        if (split) fold(Pair(emptyList<LawBook>(), emptyList<LawBook>())) { acc, t ->
            acc.copy(
                first = if (t.isFavourite) acc.first + t else acc.first,
                second = if (!t.isFavourite) acc.second + t else acc.second
            )
        }.run { first + second }
        else this

    private fun List<LawBook>.filterOnlyFavorites(filter: Boolean) =
        if (filter) filter { it.isFavourite }
        else this

    private fun List<LawBook>.filterOnlyMemberOf(filter: Boolean) =
        if (filter) filter { it.isMember }
        else this

}