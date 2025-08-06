package me.bumiller.civoris.sync.mapping

/**
 * Helps to convert a response to an entity.
 */
internal fun interface ParentEntityMapper<Response, Entity> {

    /**
     * Maps a response to an entity.
     *
     * @param response The response from the api
     * @param entity The already existing entity, or null if the entity has not yet existed
     * @param parentId The id's of the parent(s)
     * @return The new entity
     */
    fun map(response: Response, entity: Entity?, vararg parentId: Long): Entity

}