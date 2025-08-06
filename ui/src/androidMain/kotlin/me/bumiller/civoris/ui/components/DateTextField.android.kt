package me.bumiller.civoris.ui.components

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Platform method to format a [LocalDate] into the version required by the [DateTextField].
 *
 * @return The formatted string.
 */
internal actual fun LocalDate.formatDateTextField(): String {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
    return formatter.format(toJavaLocalDate())
}