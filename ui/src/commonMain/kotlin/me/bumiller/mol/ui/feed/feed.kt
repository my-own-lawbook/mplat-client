package me.bumiller.mol.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import me.bumiller.mol.common.ui.ListPosition
import me.bumiller.mol.common.ui.ListPosition.Companion.positionOf
import me.bumiller.mol.common.ui.style.disabledColor
import me.bumiller.mol.model.Identifiable
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.components.UnshiftingVisibility
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
fun <Data : Identifiable<Long>> LazyListScope.feed(
    state: SimpleState<List<Data>>,
    itemName: String,
    emptyText: String,
    isVertical: Boolean,
    fixedCrossAxisItem: Data? = null,
    content: @Composable (Data, ListPosition) -> Unit
) {
    when (state) {
        is SimpleState.Loading -> {
            item(
                key = -1
            ) {
                if (fixedCrossAxisItem != null) {
                    FixedCrossAxisSize(fixedCrossAxisItem, content, isVertical) {
                        Loading(
                            modifier = Modifier.fillMaxSize(),
                            itemName = itemName
                        )
                    }
                } else {
                    Loading(
                        modifier = Modifier.fillMaxSize(),
                        itemName = itemName
                    )
                }
            }
        }

        is SimpleState.Success -> {
            if (state.data.isEmpty()) {
                item(
                    key = -1
                ) {
                    if (fixedCrossAxisItem != null) {
                        FixedCrossAxisSize(fixedCrossAxisItem, content, isVertical) {
                            Loading(
                                modifier = Modifier.fillMaxSize(),
                                itemName = itemName
                            )
                        }
                    } else {
                        Empty(
                            modifier = Modifier.fillMaxSize(),
                            emptyText = emptyText
                        )
                    }
                }
            } else {
                items(
                    count = state.data.size,
                    key = { state.data[it].id }
                ) {
                    content(state.data[it], state.data.positionOf(state.data[it]))
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
            style = MaterialTheme.typography.bodySmall.disabledColor()
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
            text = stringResource(Res.string.feed_loading, itemName)
        )
    }
}

@Composable
private fun <Data : Identifiable<*>> FixedCrossAxisSize(
    fixedCrossAxisItem: Data,
    itemContent: @Composable (Data, ListPosition) -> Unit,
    isVertical: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    var crossAxisSize by remember {
        mutableIntStateOf(0)
    }
    Box {
        UnshiftingVisibility(
            modifier = Modifier
                .onGloballyPositioned {
                    crossAxisSize =
                        it.size.run { if (isVertical) width else height }
                },
            visible = false
        ) {
            itemContent(fixedCrossAxisItem, ListPosition.Single)
        }
        Box(
            modifier = Modifier
                .run {
                    if (isVertical) width(crossAxisSize.dp)
                    else height(crossAxisSize.dp)
                },
            content = content
        )
    }
}