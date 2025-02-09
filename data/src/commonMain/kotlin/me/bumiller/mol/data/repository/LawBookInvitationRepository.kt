package me.bumiller.mol.data.repository

import me.bumiller.mol.model.law.LawBookInvitation

/**
 * Repository that retrieves book invitation from a data source.
 */
interface LawBookInvitationRepository : SimpleRepository<Long, LawBookInvitation>