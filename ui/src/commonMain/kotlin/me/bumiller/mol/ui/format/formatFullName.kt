package me.bumiller.mol.ui.format

import me.bumiller.mol.model.law.ForeignUser

/**
 * Formats the full name of a user.
 *
 * @return The formatted full name
 */
fun ForeignUser.formatFullName(): String = "$firstName $lastName"