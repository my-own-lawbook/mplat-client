package me.bumiller.mol.sync.impl

import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.dao.EntryDao
import me.bumiller.mol.database.dao.ForeignUserDao
import me.bumiller.mol.database.dao.InvitationDao
import me.bumiller.mol.database.dao.SectionDao
import me.bumiller.mol.network.BookService
import me.bumiller.mol.network.EntryService
import me.bumiller.mol.network.ForeignUserService
import me.bumiller.mol.network.InvitationService
import me.bumiller.mol.network.SectionService
import me.bumiller.mol.sync.SyncAdapter
import me.bumiller.mol.sync.mapping.bookMapper
import me.bumiller.mol.sync.mapping.entryMapper
import me.bumiller.mol.sync.mapping.invitationMapper
import me.bumiller.mol.sync.mapping.sectionMapper
import me.bumiller.mol.sync.mapping.userMapper
import me.bumiller.mol.sync.model.SyncResult

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
        ParentSynchronizer(entryMapper, entryDao, entryService, bookService)
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