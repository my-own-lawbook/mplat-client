package me.bumiller.mol.network.model

import kotlinx.serialization.Serializable

/**
 * Contains info, possibly metadata about an http error that was sent by the server.
 */
sealed interface ErrorInfo {

    /**
     * No serializable info was passed.
     *
     * @param message The raw body as text.
     */
    data class NoInfo(val message: String) : ErrorInfo

    /**
     * Error body for a 409 response.
     */
    @Serializable
    data class ConflictUniqueInfo(

        /**
         * The field that already has the value set
         */
        val field: String,

        /**
         * The value of the field
         */
        val value: String

    ) : ErrorInfo

    /**
     * Error body for a 404 response
     */
    @Serializable
    data class NotFoundIdentifierInfo(

        /**
         * The name of the resource type
         */
        val resourceType: String,

        /**
         * The identifier that was searched by
         */
        val identifier: String

    ) : ErrorInfo

    /**
     * Error body for 400 when a format rule was violated
     */
    @Serializable
    data class BadFormatInfo(

        /**
         * The required format
         */
        val format: String,

        /**
         * The passed value
         */
        val value: String

    ) : ErrorInfo

    /**
     * Error info for when a description is the only way to describe the error
     */
    @Serializable
    data class DescriptionInfo(

        /**
         * The description
         */
        val description: String

    ) : ErrorInfo

}