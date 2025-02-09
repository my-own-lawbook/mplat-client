package me.bumiller.mol.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.base.SimpleEntity
import me.bumiller.mol.model.Identifiable

/**
 * Abstract implementation of a [SimpleRepository] that preconfigures the connection to a dao.
 */
abstract class DatabaseSimpleRepository<Model : Identifiable<Long>, Entity : SimpleEntity, Dao : SimpleDao<Entity>>(

    /**
     * The dao to access the database.
     */
    private val dao: Dao

) : SimpleRepository<Long, Model> {

    /**
     * Creates a [Model] to return for a given database entity.
     *
     * @param entity The entity of the database
     * @return The model corresponding to the database entity
     */
    abstract suspend fun createModelFor(entity: Entity): Model

    override fun getAll(): Flow<List<Model>> =
        dao.getAll()
            .map { entities -> entities.map { createModelFor(it) } }

    override fun getById(id: Long): Flow<Model?> =
        dao.getById(id)
            .map { entity -> entity?.let { createModelFor(it) } }

}