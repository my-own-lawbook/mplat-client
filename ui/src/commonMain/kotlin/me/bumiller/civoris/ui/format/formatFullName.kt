package me.bumiller.civoris.ui.format

import me.bumiller.civoris.model.law.ForeignUser

/**
 * Formats the full name of a user.
 *
 * @return The formatted full name
 */
fun ForeignUser.formatFullName(): String = "$firstName $lastName"