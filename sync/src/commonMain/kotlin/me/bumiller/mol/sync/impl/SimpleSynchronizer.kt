package me.bumiller.mol.sync.impl

import kotlinx.coroutines.flow.first
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.base.SimpleEntity
import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.RestResponse
import me.bumiller.mol.sync.Synchronizer
import me.bumiller.mol.sync.mapping.EntityMapper

internal class SimpleSynchronizer<Response : RestResponse, Entity : SimpleEntity>(
    private val mapper: EntityMapper<Response, Entity>,
    private val dao: SimpleDao<Entity>,
    private val service: SimpleResourceService<Response>
) : Synchronizer {

    /**
     * Synchronizes all responses from the service into the local daos
     *
     * @return Whether the data could be fetched from the api
     */
    override suspend fun synchronize(): Boolean {
        val responses = service.getAll().dataOrNull() ?: return false

        responses.forEach { response ->
            syncResponse(response)
        }

        return true
    }

    private suspend fun syncResponse(response: Response) {
        val entity = dao.getById(response.id).first()
        if (entity == null) {
            val createdEntity = mapper.map(response, null)
            dao.insert(createdEntity)
        } else {
            val updatedEntity = mapper.map(response, entity)
            dao.update(updatedEntity)
        }
    }

}