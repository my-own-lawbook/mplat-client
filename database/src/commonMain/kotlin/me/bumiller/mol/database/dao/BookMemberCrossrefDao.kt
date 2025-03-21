package me.bumiller.mol.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.bumiller.mol.database.dao.base.CrossrefDao
import me.bumiller.mol.database.entities.BookMemberCrossref

/**
 * Class that manages querying of [BookMemberCrossref].
 */
@Dao
interface BookMemberCrossrefDao : CrossrefDao<BookMemberCrossref> {

    @Query("SELECT * FROM bookmembercrossref")
    override fun getAll(): Flow<List<BookMemberCrossref>>

}