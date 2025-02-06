package me.bumiller.mol.database.dao

import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.database.test.SimpleDaoTest
import org.junit.jupiter.api.Test

/**
 * Tests for the [BookDao].
 */
class BookDaoTest : SimpleDaoTest<BookEntity, BookDao>() {

    override val dao: BookDao
        get() = bookDao

    override fun createEntity(key: Long) = bookEntity(key)

    override fun BookEntity.performUpdate() = copy(
        key = "key updated",
        name = "name updated",
        description = "description updated",
        isFavourite = !isFavourite
    )

    override fun BookEntity.copyId(id: Long) = copy(id = id)

    @Test
    fun triggerParentTests() {
    }
}