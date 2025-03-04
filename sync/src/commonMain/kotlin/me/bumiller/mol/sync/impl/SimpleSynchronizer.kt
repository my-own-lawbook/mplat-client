package me.bumiller.mol.sync.impl

import kotlinx.coroutines.flow.first
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.base.SimpleEntity
import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.RestResponse
import me.bumiller.mol.sync.Synchronizer
import me.bumiller.mol.sync.mapping.EntityMapper
import me.bumiller.mol.sync.model.SyncResult

internal class SimpleSynchronizer<Response : RestResponse, Entity : SimpleEntity>(
    private val mapper: EntityMapper<Response, Entity>,
    private val dao: SimpleDao<Entity>,
    private val service: SimpleResourceService<Response>
) : Synchronizer {

    override suspend fun synchronize(): SyncResult {
        val responses = service.getAll().run {
            dataOrNull() ?: return SyncResult.Network(this)
        }

        responses.forEach { response ->
            syncResponse(response)
        }

        return SyncResult.Success
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