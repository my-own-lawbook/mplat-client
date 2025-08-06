package me.bumiller.civoris.data.repository

import me.bumiller.civoris.model.law.LawBookInvitation

/**
 * Repository that retrieves book invitation from a data source.
 */
interface LawBookInvitationRepository : SimpleRepository<Long, LawBookInvitation>