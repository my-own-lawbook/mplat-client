package me.bumiller.civoris.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

/**
 * Type converter for the [Instant] type.
 *
 * This will converts ISO-8601 strings and [Instant]s.
 */
object InstantConverter {

    /**
     * Converts a string to the appropriate [Instant].
     *
     * @param string The ISO-8601 strings string to convert
     * @return The [Instant]
     */
    @TypeConverter
    fun stringToInstant(string: String?): Instant? = string?.let(Instant::parse)

    /**
     * Converts an [Instant] to the appropriate string.
     *
     * @param instant The instant to convert
     * @return The ISO-8601 string
     */
    @TypeConverter
    fun instantToIsoString(instant: Instant?): String? = instant?.toString()

}