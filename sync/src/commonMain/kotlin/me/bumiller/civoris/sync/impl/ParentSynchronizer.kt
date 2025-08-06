package me.bumiller.civoris.sync.impl

import kotlinx.coroutines.flow.first
import me.bumiller.civoris.database.dao.base.SimpleDao
import me.bumiller.civoris.database.entities.base.SimpleEntity
import me.bumiller.civoris.model.sync.SyncResult
import me.bumiller.civoris.network.base.ResourceParentService
import me.bumiller.civoris.network.base.SimpleResourceService
import me.bumiller.civoris.network.response.RestResponse
import me.bumiller.civoris.sync.Synchronizer
import me.bumiller.civoris.sync.mapping.ParentEntityMapper

internal class ParentSynchronizer<Response : RestResponse, Entity : SimpleEntity, Parent : RestResponse>(
    private val mapper: ParentEntityMapper<Response, Entity>,
    private val dao: SimpleDao<Entity>,
    private val service: ResourceParentService<Response>,
    private val parentService: SimpleResourceService<Parent>,
    private val syncForParent: suspend (Parent) -> Boolean = { true }
) : Synchronizer {

    override suspend fun synchronize(): SyncResult {
        val parentResponses = parentService.getAll().run {
            dataOrNull() ?: return SyncResult.Network
        }

        val allResponses = mutableListOf<Response>()

        for (parent in parentResponses) {
            if (!syncForParent(parent)) continue
            val responses = service.getByParent(parent.id).run {
                dataOrNull() ?: return SyncResult.Network
            }

            responses.forEach {
                syncResponse(it, parent.id)
            }

            allResponses.addAll(responses)
        }

        deleteOldLocals(allResponses)

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

    private suspend fun syncResponse(response: Response, parentId: Long) {
        val entity = dao.getById(response.id).first()
        if (entity == null) {
            val createdEntity = mapper.map(response, null, parentId)
            dao.insert(createdEntity)
        } else {
            val updatedEntity = mapper.map(response, entity, parentId)
            dao.update(updatedEntity)
        }
    }

}