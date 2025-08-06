package me.bumiller.civoris.sync.impl

import kotlinx.coroutines.flow.first
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.base.SimpleEntity
import me.bumiller.civoris.model.sync.SyncResult
import me.bumiller.civoris.network.base.SimpleResourceService
import me.bumiller.civoris.network.response.RestResponse
import me.bumiller.civoris.sync.Synchronizer
import me.bumiller.civoris.sync.mapping.EntityMapper

internal class SimpleSynchronizer<Response : RestResponse, Entity : SimpleEntity>(
    private val mapper: EntityMapper<Response, Entity>,
    private val dao: SimpleDao<Entity>,
    private val service: SimpleResourceService<Response>
) : Synchronizer {

    override suspend fun synchronize(): SyncResult {
        val responses = service.getAll().run {
            dataOrNull() ?: return SyncResult.Network
        }

        responses.forEach { response ->
            syncResponse(response)
        }

        deleteOldLocals(responses)

        return SyncResult.Success
    }

    private suspend fun deleteOldLocals(responses: List<Response>) {
        val entities = dao.getAll().first()

        entities.forEach { entity ->
            if (responses.none { it.id == entity.id }) {
                dao.delete(entity)
            }
        }
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