package me.bumiller.civoris.network.base

import me.bumiller.civoris.network.model.NetworkResponse

/**
 * Base class for a resource service that also allows receiving a collection of resources based on a parent.
 */
interface ResourceParentService<ResponseBody> : SimpleResourceService<ResponseBody> {

    /**
     * Gets all resources for a specific parent.
     *
     * @param parentId The id of the parent
     * @return The resources that have the parent as a parent
     */
    suspend fun getByParent(parentId: Long): NetworkResponse<List<ResponseBody>>

}