package me.bumiller.mol.database.dao

import me.bumiller.mol.database.entities.BookMemberCrossref
import me.bumiller.mol.database.test.CrossrefDaoTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Tests for the [BookMemberCrossrefDao]
 */
class BookMemberCrossrefDaoTest : CrossrefDaoTest<BookMemberCrossref, BookMemberCrossrefDao>() {

    override val dao: BookMemberCrossrefDao
        get() = bookMemberDao

    override fun createEntity(parentId: Long, childId: Long) =
        BookMemberCrossref(parentId, childId, "role ${parentId + childId}")

    override fun BookMemberCrossref.performUpdate() = copy(
        role = "role updated"
    )

    @Test
    @DisplayName("<Triggering parent tests>")
    fun triggerParentTests() {
    }

}