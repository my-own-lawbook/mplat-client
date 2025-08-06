package me.bumiller.civoris.sync.impl

import me.bumiller.civoris.database.dao.BookDao
import me.bumiller.civoris.database.dao.EntryDao
import me.bumiller.civoris.database.dao.ForeignUserDao
import me.bumiller.civoris.database.dao.InvitationDao
import me.bumiller.civoris.database.dao.SectionDao
import me.bumiller.civoris.model.sync.SyncResult
import me.bumiller.civoris.network.BookService
import me.bumiller.civoris.network.EntryService
import me.bumiller.civoris.network.ForeignUserService
import me.bumiller.civoris.network.InvitationService
import me.bumiller.civoris.network.SectionService
import me.bumiller.civoris.sync.SyncAdapter
import me.bumiller.civoris.sync.mapping.bookMapper
import me.bumiller.civoris.sync.mapping.entryMapper
import me.bumiller.civoris.sync.mapping.invitationMapper
import me.bumiller.civoris.sync.mapping.sectionMapper
import me.bumiller.civoris.sync.mapping.userMapper

internal class NetworkDatabaseSyncAdapter(
    bookService: BookService,
    entryService: EntryService,
    sectionService: SectionService,
    foreignUserService: ForeignUserService,
    invitationService: InvitationService,
    bookDao: BookDao,
    entryDao: EntryDao,
    sectionDao: SectionDao,
    invitationDao: InvitationDao,
    foreignUserDao: ForeignUserDao
) : SyncAdapter {

    private val bookSynchronizer = SimpleSynchronizer(bookMapper, bookDao, bookService)
    private val invitationSynchronizer =
        SimpleSynchronizer(invitationMapper, invitationDao, invitationService)
    private val userSynchronizer =
        SimpleSynchronizer(userMapper, foreignUserDao, foreignUserService)

    private val entrySynchronizer =
        ParentSynchronizer(entryMapper, entryDao, entryService, bookService) { it.isMemberOf }
    private val sectionSynchronizer =
        ParentSynchronizer(sectionMapper, sectionDao, sectionService, entryService)

    override suspend fun performSync(): SyncResult {
        val synchronizers = listOf(
            bookSynchronizer,
            invitationSynchronizer,
            userSynchronizer,
            entrySynchronizer,
            sectionSynchronizer
        )

        synchronizers.forEach { synchronizer ->
            synchronizer.synchronize().run {
                if (isFailed) return this
            }
        }

        return SyncResult.Success
    }

}