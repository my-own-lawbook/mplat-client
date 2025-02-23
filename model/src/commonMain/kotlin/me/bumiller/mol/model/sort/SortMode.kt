package me.bumiller.mol.model.sort

/**
 * Models a way to sort a collection of objects.
 */
sealed interface SortMode {

    /**
     * Sort the objects alphabetically by name.
     */
    data object Name : SortMode

    /**
     * Sort the objects by an abstract definition of creation or receiving date
     */
    data object Created : SortMode

}