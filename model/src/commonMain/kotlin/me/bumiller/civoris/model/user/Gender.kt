package me.bumiller.civoris.model.user

import kotlinx.serialization.Serializable
import me.bumiller.civoris.model.serializer.GenderSerializer

/**
 * Contains information about a gender.
 */
@Serializable(with = GenderSerializer::class)
sealed interface Gender {

    /**
     * Male gender.
     */
    data object Male : Gender

    /**
     * Female gender.
     */
    data object Female : Gender

    /**
     * Other gender.
     */
    data object Other : Gender

    /**
     * User does not want to say.
     */
    data object NotSay : Gender

    companion object {

        /**
         * All genders.
         */
        fun values() = listOf(Male, Female, Other, NotSay)

    }

}