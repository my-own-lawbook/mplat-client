package me.bumiller.civoris.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.bumiller.civoris.model.law.LawBook
import me.bumiller.civoris.ui.layout.ColumnCard

private val CardWidth = 200.dp

/**
 * A card displaying basic attributes of a law-book.
 *
 * @param modifier The modifier
 * @param book The book to show the properties of
 * @param onClick The callback for when the card is clicked
 */
@Composable
fun LawBookCard(
    modifier: Modifier = Modifier,
    book: LawBook,
    onClick: (LawBook) -> Unit = {}
) {
    ColumnCard(
        modifier = modifier
            .width(CardWidth),
        onClick = { onClick(book) }
    ) {
        KeySquare(
            modifier = Modifier
                .fillMaxWidth()
                .height(CardWidth),
            shape = CardDefaults.elevatedShape,
            book = book
        )

        Column(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = book.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun KeySquare(
    modifier: Modifier = Modifier,
    shape: Shape,
    book: LawBook
) {
    val initialFontSize = MaterialTheme.typography.displayLarge.fontSize
    var fontSize by remember {
        mutableStateOf(initialFontSize)
    }

    val style = MaterialTheme.typography.titleLarge.copy(
        color = Color.White,
        fontSize = fontSize
    )

    Box(
        modifier = modifier
            .background(
                color = cardColors[book.id.toInt() % cardColors.size],
                shape = shape
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = book.key,
            style = style,
            maxLines = 1,
            onTextLayout = {
                if (it.hasVisualOverflow) {
                    fontSize *= 0.9
                }
            }
        )
    }
}

private val cardColors = listOf(
    Color(0xFFFFB5E8),
    Color(0xFFAFF8D8),
    Color(0xFFFFABAB),
    Color(0xFFDCD3FF),
    Color(0xFFF6A6FF),
    Color(0xFFACE7FF)
)