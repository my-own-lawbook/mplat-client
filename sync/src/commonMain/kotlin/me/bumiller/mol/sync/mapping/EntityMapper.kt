package me.bumiller.mol.sync.mapping

/**
 * Helps to convert a response to an entity.
 */
internal fun interface EntityMapper<Response, Entity> {

    /**
     * Maps a response to an entity.
     *
     * @param response The response from the api
     * @param entity The already existing entity, or null if the entity has not yet existed
     * @return The new entity
     */
    fun map(response: Response, entity: Entity?): Entity

}