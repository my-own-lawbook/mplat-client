package me.bumiller.mol.sync.impl

import kotlinx.coroutines.flow.first
import me.bumiller.mol.database.dao.base.SimpleDao
import me.bumiller.mol.database.entities.base.SimpleEntity
import me.bumiller.mol.network.base.ResourceParentService
import me.bumiller.mol.network.base.SimpleResourceService
import me.bumiller.mol.network.response.RestResponse
import me.bumiller.mol.sync.Synchronizer
import me.bumiller.mol.sync.mapping.ParentEntityMapper

internal class ParentSynchronizer<Response : RestResponse, Entity : SimpleEntity, Parent : RestResponse>(
    private val mapper: ParentEntityMapper<Response, Entity>,
    private val dao: SimpleDao<Entity>,
    private val service: ResourceParentService<Response>,
    private val parentService: SimpleResourceService<Parent>
) : Synchronizer {

    /**
     * Syncs all the entities for a specified parent.
     *
     * @return Whether the resources were able to be fetched from the api
     */
    override suspend fun synchronize(): Boolean {
        val parentResponses = parentService.getAll().dataOrNull() ?: return false

        parentResponses.forEach { parent ->
            val responses = service.getByParent(parent.id).dataOrNull() ?: return false

            responses.forEach {
                syncResponse(it, parent.id)
            }
        }

        return true
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