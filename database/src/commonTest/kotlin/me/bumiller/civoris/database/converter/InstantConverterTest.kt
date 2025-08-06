package me.bumiller.civoris.database.converter

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Tests for the [InstantConverter] type converter.
 */
class InstantConverterTest {

    private val converter = InstantConverter

    @Test
    @DisplayName("Converting instants to string returns the expected ISO-8601 string.")
    fun instantToStringWorks() {
        val toTest = mapOf(
            Clock.System.now().let { it to it.toString() },
            Instant.parse("2025-02-06T17:18:22Z") to "2025-02-06T17:18:22Z",
            Instant.fromEpochSeconds(1738862302) to "2025-02-06T17:18:22Z"
        )

        toTest.forEach { (instant, expected) ->
            Assertions.assertEquals(expected, converter.instantToIsoString(instant))
        }
    }

    @Test
    @DisplayName("Converting ISO-8601 strings to instants returns the expected instant.")
    fun stringToInstantWorks() {
        val toTest = mapOf(
            Clock.System.now().let { it.toString() to it },
            "2025-02-06T17:18:22Z" to Instant.parse("2025-02-06T17:18:22Z"),
            "2025-02-06T17:18:22Z" to Instant.fromEpochSeconds(1738862302)
        )

        toTest.forEach { (instant, expected) ->
            Assertions.assertEquals(expected, converter.stringToInstant(instant))
        }
    }

}