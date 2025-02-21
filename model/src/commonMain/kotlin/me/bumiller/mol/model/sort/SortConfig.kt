package me.bumiller.mol.model.sort

/**
 * Bundles a sort mode and sort direction.
 *
 * @param direction The direction in which to sort the collection
 * @param mode By what attribute to sort the collection
 */
data class SortConfig(val direction: SortDirection?, val mode: SortMode?) {

    companion object {

        /**
         * A sort config that won't cause a change in the list.
         */
        val None = SortConfig(null, null)

    }

}
