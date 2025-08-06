package me.bumiller.civoris.database.dao

import me.bumiller.civoris.database.entities.BookEntity
import me.bumiller.civoris.database.test.SimpleDaoTest
import org.junit.jupiter.api.DisplayName
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
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
        // Stub method to trigger implicit tests from parent class
    }
}