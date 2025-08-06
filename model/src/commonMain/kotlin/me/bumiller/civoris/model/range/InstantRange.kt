package me.bumiller.civoris.model.range

import kotlinx.datetime.Instant

/**
 * Range between two timestamps instances.
 */
data class InstantRange(
    override val start: Instant,
    override val endInclusive: Instant
) : ClosedRange<Instant>

/**
 * Creates an InstantRange until (inclusive) a specified other instant.
 *
 * @param other The other instant
 * @return The range
 */
operator fun Instant.rangeTo(other: Instant) = InstantRange(this, other)