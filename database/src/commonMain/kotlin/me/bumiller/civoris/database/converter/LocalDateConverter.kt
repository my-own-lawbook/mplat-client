package me.bumiller.civoris.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

/**
 * Type converter for the [LocalDate] type.
 *
 * This will converts ISO-8601 strings and [LocalDate]s.
 */
object LocalDateConverter {

    /**
     * Converts a string to the appropriate [LocalDate].
     *
     * @param string The ISO-8601 string to convert
     * @return The [LocalDate]
     */
    @TypeConverter
    fun stringToLocalDate(string: String?): LocalDate? = string?.let(LocalDate::parse)

    /**
     * Converts a [LocalDate] to the appropriate string.
     *
     * @param date The date to convert
     * @return The ISO-8601 string
     */
    @TypeConverter
    fun localDateToString(date: LocalDate?): String? = date?.toString()

}