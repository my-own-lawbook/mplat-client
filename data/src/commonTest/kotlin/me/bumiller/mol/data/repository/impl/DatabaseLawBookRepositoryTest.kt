package me.bumiller.mol.data.repository.impl

import io.mockk.mockk
import me.bumiller.mol.data.test.DatabaseSimpleRepositoryTest
import me.bumiller.mol.database.dao.BookDao
import me.bumiller.mol.database.entities.BookEntity
import me.bumiller.mol.model.law.LawBook
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