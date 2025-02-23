package me.bumiller.mol.ui.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

/**
 * Models the different types of cards.
 */
enum class CardType(

    /**
     * Producer for the default shape.
     */
    val defaultShape: @Composable () -> Shape,

    /**
     * Producer for the default colors.
     */
    val defaultColors: @Composable () -> CardColors,

    /**
     * Producer for the default border.
     */
    val defaultBorder: @Composable () -> BorderStroke?,

    /**
     * Producer for the default elevation.
     */
    val defaultElevation: @Composable () -> CardElevation

) {

    /**
     * The elevated card.
     */
    Elevated(
        { CardDefaults.elevatedShape },
        { CardDefaults.elevatedCardColors() },
        { null },
        { CardDefaults.elevatedCardElevation() }),

    /**
     * The outlined card.
     */
    Outlined(
        { CardDefaults.outlinedShape },
        { CardDefaults.outlinedCardColors() },
        { CardDefaults.outlinedCardBorder() },
        { CardDefaults.outlinedCardElevation() }),

    /**
     * The normal card.
     */
    Normal(
        { CardDefaults.shape },
        { CardDefaults.cardColors() },
        { null },
        { CardDefaults.cardElevation() })

}

/**
 * Card wrapper that eases the configuration and padding. Embeds a column that is also configurable.
 */
@Composable
fun ColumnCard(
    modifier: Modifier = Modifier,
    cardType: CardType = CardType.Normal,
    padding: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = cardType.defaultShape(),
    colors: CardColors = cardType.defaultColors(),
    border: BorderStroke? = cardType.defaultBorder(),
    elevation: CardElevation = cardType.defaultElevation(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardContent: @Composable ColumnScope.() -> Unit = @Composable {
        Column(
            modifier = Modifier
                .padding(padding),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }

    when (cardType) {
        CardType.Elevated -> ElevatedCard(
            onClick,
            modifier,
            enabled,
            shape,
            colors,
            elevation,
            interactionSource,
            cardContent
        )

        CardType.Outlined -> OutlinedCard(
            onClick,
            modifier,
            enabled,
            shape,
            colors,
            elevation,
            border ?: CardDefaults.outlinedCardBorder(),
            interactionSource,
            cardContent
        )

        CardType.Normal -> Card(
            onClick,
            modifier,
            enabled,
            shape,
            colors,
            elevation,
            border,
            interactionSource,
            cardContent
        )
    }
}