package me.bumiller.mol.domain.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

/**
 * Automatically combines and flattens the result of mapping each flow element to another flow.
 *
 * @param mapper The mapper that gives each element of the flow another flow
 * @return The flattened resulting flow
 */
@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, reified R> Flow<List<T>>.flatMapMergeCombine(crossinline mapper: (T) -> Flow<R>): Flow<List<R>> {
    return flatMapLatest { items ->
        val flows = items.map(mapper)

        combineSafe(flows) { it.toList() }
    }
}