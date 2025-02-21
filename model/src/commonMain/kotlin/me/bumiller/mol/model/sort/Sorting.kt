package me.bumiller.mol.model.sort

import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.law.LawBookInvitation

/**
 * Sorts a list of law books.
 *
 * @param config The config by which to sort the list
 * @return The sorted list
 */
fun List<LawBook>.sortWith(config: SortConfig): List<LawBook> =
    when (config.mode) {
        SortMode.Name -> sortedBy(LawBook::name)
        else -> this
    }.run {
        if (config.direction == SortDirection.Descending) reversed()
        else this
    }

/**
 * Sorts a list of invitations
 *
 * @param config The config by which to sort the list
 * @return The sorted list
 */
fun List<LawBookInvitation>.sortWith(config: SortConfig): List<LawBookInvitation> =
    when (config.mode) {
        is SortMode.Created -> sortedBy(LawBookInvitation::sentTimestamp)
        else -> this
    }.run {
        if (config.direction == SortDirection.Descending) reversed()
        else this
    }