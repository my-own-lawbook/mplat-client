package me.bumiller.mol.model

/**
 * Base class for any type that has an id attached to it.
 */
interface Identifiable<Id> {

    /**
     * The id of the object.
     */
    val id: Id

}