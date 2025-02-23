package me.bumiller.mol.network.base

import me.bumiller.mol.network.model.NetworkResponse

/**
 * Base class for any service that accesses a simple crud resource on the rest api.
 *
 * @param ResponseBody The class that acts as the response body for a resource return
 */
interface SimpleResourceService<ResponseBody> {

    /**
     * Gets all of the resources for the currently authenticated user.
     *
     * @return A response of all the resources
     */
    suspend fun getAll(): NetworkResponse<List<ResponseBody>>

}