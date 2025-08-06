package me.bumiller.civoris.data.repository.impl

import io.mockk.mockk
import me.bumiller.civoris.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.civoris.database.dao.BookDao
import me.bumiller.civoris.database.entities.BookEntity
import me.bumiller.civoris.model.law.LawBook
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class DatabaseLawBookRepositoryTest :
    DatabaseSimpleRepositoryTest<LawBook, BookEntity, BookDao, DatabaseLawBookRepository>() {

    override fun createRepository(dao: BookDao) = DatabaseLawBookRepository(dao)

    override fun createMock() = mockk<BookDao>()

    override fun createEntity(key: Long) = bookEntity(key)

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }
}