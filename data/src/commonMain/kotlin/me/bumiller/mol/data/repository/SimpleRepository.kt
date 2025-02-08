package me.bumiller.mol.data.repository

import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.model.Identifiable

/**
 * Base class for any repository which is supposed to retrieve entities from a data source.
 */
interface SimpleRepository<Id, Data : Identifiable<Id>> {

    /**
     * Gets an entity by the specified id.
     *
     * @param id The id of the entity
     * @return A flow of the entity. Null if the entity was not found.
     */
    fun getById(id: Id): Flow<Data?>

    /**
     * Gets all entities.
     *
     * @return The flow of all entities
     */
    fun getAll(): Flow<List<Data>>

}