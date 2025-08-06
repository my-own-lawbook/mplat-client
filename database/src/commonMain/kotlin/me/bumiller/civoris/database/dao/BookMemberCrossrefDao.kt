package me.bumiller.civoris.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.civoris.database.dao.base.CrossrefDao
import me.bumiller.civoris.database.entities.BookMemberCrossref

/**
 * Class that manages querying of [BookMemberCrossref].
 */
@Dao
interface BookMemberCrossrefDao : CrossrefDao<BookMemberCrossref> {

    @Query("SELECT * FROM bookmembercrossref")
    override fun getAll(): Flow<List<BookMemberCrossref>>

}