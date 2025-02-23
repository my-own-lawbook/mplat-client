package me.bumiller.mol.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.bumiller.mol.common.ui.ListPosition
import me.bumiller.mol.common.ui.ListPosition.Companion.positionOf
import me.bumiller.mol.common.ui.style.disabledColor
import me.bumiller.mol.model.Identifiable
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.feed_loading
import org.jetbrains.compose.resources.stringResource

/**
 * Creates a feed in a lazy list.
 *
 * @param state The state containing the items
 * @param itemName The name of the type of item that is displayed
 * @param emptyText The text that is shown when the list is empty
 * @param isVertical Whether the list is a vertical list, or false otherwise
 * @param fixedCrossAxisItem The item that is used to ensure the feed has the same height (horizontal
 * @param content The content for a single item
 * lazy list) or same width (vertical lazy list), even if no items are displayed. If null is passed,
 * this control is not enforced.
 */
@Composable
fun <Data : Identifiable<Long>> Feed(
    modifier: Modifier = Modifier,
    state: SimpleState<List<Data>>,
    isVertical: Boolean,
    itemName: String,
    emptyText: String,
    content: @Composable (Data, ListPosition) -> Unit
) {
    when (state) {
        is SimpleState.Loading -> {
            Loading(
                modifier = modifier,
                itemName = itemName
            )
        }

        is SimpleState.Success -> {
            if (state.data.isEmpty()) {
                Empty(
                    modifier = modifier,
                    emptyText = emptyText
                )
            } else {
                val itemsDeclaration: LazyListScope.() -> Unit = {
                    items(
                        count = state.data.size,
                        key = { state.data[it].id }
                    ) {
                        content(state.data[it], state.data.positionOf(state.data[it]))
                    }
                }

                if (isVertical) {
                    LazyColumn(
                        modifier = modifier,
                        content = itemsDeclaration
                    )
                } else {
                    LazyRow(
                        modifier = modifier,
                        content = itemsDeclaration
                    )
                }
            }
        }

        else -> {
            // Not handling error states
        }
    }
}

@Composable
private fun Empty(
    modifier: Modifier = Modifier,
    emptyText: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emptyText,
            style = MaterialTheme.typography.bodySmall.disabledColor(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
    itemName: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            text = stringResource(Res.string.feed_loading, itemName),
            style = MaterialTheme.typography.bodySmall.disabledColor(),
            textAlign = TextAlign.Center
        )
    }
}