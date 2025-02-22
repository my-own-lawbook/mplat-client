package me.bumiller.mol.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.bumiller.mol.model.law.LawBook
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.book_feed_empty_text
import me.bumiller.mol.ui.book_feed_item_name
import me.bumiller.mol.ui.components.LawBookCard
import org.jetbrains.compose.resources.stringResource

private val DummyBook = LawBook(
    id = 1L,
    key = "---",
    name = "----",
    description = "- ".repeat(200),
    isFavourite = false
)

/**
 * Composable for a horizontally lazy list that displays several law books.
 *
 * @param modifier The modifier
 * @param state The state of the law books
 * @param onClick The callback when an item is clicked
 */
@Composable
fun BookCardFeed(
    modifier: Modifier = Modifier,
    state: SimpleState<List<LawBook>>,
    onClick: (LawBook) -> Unit
) {
    val itemName = stringResource(Res.string.book_feed_item_name)
    val emptyText = stringResource(Res.string.book_feed_empty_text)

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        feed(
            state = state,
            itemName = itemName,
            emptyText = emptyText,
            false,
            fixedCrossAxisItem = DummyBook
        ) { item, _ ->
            LawBookCard(
                modifier = Modifier,
                book = item,
                onClick = onClick
            )
        }
    }
}