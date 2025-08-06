package me.bumiller.civoris.database.converter

import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Tests for the [LocalDateConverter] type converter.
 */
class LocalDateConverterTest {

    private val converter = LocalDateConverter

    @Test
    @DisplayName("Converting LocalDate's to string returns the expected ISO-8601 string.")
    fun instantToStringWorks() {
        val toTest = mapOf(
            LocalDate(2004, 7, 11) to "2004-07-11",
            LocalDate(2674, 1, 9) to "2674-01-09",
        )

        toTest.forEach { (instant, expected) ->
            Assertions.assertEquals(expected, LocalDateConverter.localDateToString(instant))
        }
    }

    @Test
    @DisplayName("Converting ISO-8601 strings to instants returns the expected instant.")
    fun stringToInstantWorks() {
        val toTest = mapOf(
            "2004-07-11" to LocalDate(2004, 7, 11),
            "2674-01-09" to LocalDate(2674, 1, 9),
        )

        toTest.forEach { (instant, expected) ->
            Assertions.assertEquals(expected, LocalDateConverter.stringToLocalDate(instant))
        }
    }

}