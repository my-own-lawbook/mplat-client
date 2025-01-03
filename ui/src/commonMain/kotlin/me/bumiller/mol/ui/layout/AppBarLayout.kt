package me.bumiller.mol.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import me.bumiller.mol.ui.components.MolAppBar
import me.bumiller.mol.ui.components.TopAppBarStyles
import me.bumiller.mol.ui.locals.LocalWindowSizeClass

@Composable
private fun calculatePadding(): PaddingValues {
    val horizontal = when (LocalWindowSizeClass.current.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 16.dp
        WindowWidthSizeClass.Medium -> 32.dp
        WindowWidthSizeClass.Expanded -> 128.dp
        else -> throw IllegalStateException("Invalid window width size class.")
    }

    val bottom = when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> 8.dp
        WindowHeightSizeClass.Medium -> 32.dp
        WindowHeightSizeClass.Expanded -> 64.dp
        else -> throw IllegalStateException("Invalid window width size class.")
    }

    val top = 8.dp

    return PaddingValues(horizontal, top, horizontal, bottom)
}

/**
 * Finalized layout direction of an [AppBarLayout].
 */
enum class CanonicalLayoutType(

    /**
     * True if it is a column, false otherwise.
     */
    val isVertical: Boolean

) {

    /**
     * Vertical direction
     */
    Column(true),

    /**
     * Horizontal direction
     */
    Row(false)

}

/**
 * Different types to layout the children.
 */
sealed class AppBarLayoutType(

    /**
     * Spacing between the two units
     */
    open val spacing: Dp,

    /**
     * Allowed height of the first content in relation to the second one.
     */
    open val verticalFirstWeightRange: ClosedFloatingPointRange<Float>?,

    /**
     * Allowed width of the first content in relation to the second one.
     */
    open val horizontalFirstWeightRange: ClosedFloatingPointRange<Float>?,

    ) {

    /**
     * Layout the children in a column.
     *
     * @param spacing Spacing between the two units
     */
    data class Column(
        override val spacing: Dp = 0.dp,
        override val verticalFirstWeightRange: ClosedFloatingPointRange<Float>? = 0F..1F
    ) : AppBarLayoutType(spacing, verticalFirstWeightRange, null)

    /**
     * Layout the children in a row.
     *
     * @param spacing Spacing between the two units
     */
    data class Row(
        override val spacing: Dp = 0.dp,
        override val horizontalFirstWeightRange: ClosedFloatingPointRange<Float>? = 0F..1f
    ) : AppBarLayoutType(spacing, null, horizontalFirstWeightRange)

    /**
     * Positions the two units according to the available space
     *
     * @param spacing Spacing between the two units
     */
    data class SizeAware(
        override val spacing: Dp = 8.dp,
        override val verticalFirstWeightRange: ClosedFloatingPointRange<Float>? = 0F..1F,
        override val horizontalFirstWeightRange: ClosedFloatingPointRange<Float>? = 0F..1F
    ) : AppBarLayoutType(spacing, verticalFirstWeightRange, horizontalFirstWeightRange)

}

/**
 * Wrapper for the [AppBarLayout] that handles a specific type of layout where the first content is an image and a descriptive text.
 */
@Composable
fun AppBarLayoutWithImage(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = calculatePadding(),
    appBarStyle: TopAppBarStyles = TopAppBarStyles.Centered,
    verticalScrollable: Boolean = true,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    imagePainter: Painter,
    imageContentDescription: String? = null,
    description: @Composable () -> Unit,
    content: @Composable (CanonicalLayoutType) -> Unit
) {
    val sizeClass = LocalWindowSizeClass.current
    val heightClass = sizeClass.heightSizeClass

    val includeImageVertical = heightClass >= WindowHeightSizeClass.Expanded
    val includeImageHorizontal = heightClass >= WindowHeightSizeClass.Medium

    // When the image is included in vertical mode, we want to allocate more maximum space.
    val verticalRange = if (includeImageVertical) 0F..0.3F
    else 0F..0.4F

    AppBarLayout(
        modifier = modifier,
        layoutType = AppBarLayoutType.SizeAware(
            spacing = 32.dp,
            verticalFirstWeightRange = verticalRange,
            horizontalFirstWeightRange = 0.0F..0.4F
        ),
        verticalScrollable = verticalScrollable,
        contentPadding = contentPadding,
        appBarStyle = appBarStyle,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        firstContent = { layoutType ->
            val includeImage = when (layoutType) {
                CanonicalLayoutType.Column -> includeImageVertical
                CanonicalLayoutType.Row -> includeImageHorizontal
            }

            Column {
                if (includeImage) {
                    val imageModifier = if (layoutType.isVertical)
                        Modifier
                            .weight(1F)
                    else
                        Modifier
                            .heightIn(max = 300.dp)

                    Image(
                        modifier = imageModifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        painter = imagePainter,
                        contentDescription = imageContentDescription
                    )
                }

                description()
            }
        },
        secondContent = content
    )
}

/**
 * Layout that combines two chunks of content with an app bar.
 *
 * @param modifier The modifier
 * @param layoutType The layout type that defines how the two content chunks will be combined
 * @param contentPadding The padding added inside the layout
 * @param appBarStyle The style of the app bar
 * @param title The title
 * @param navigationIcon The navigation icon
 * @param actions The actions
 * @param firstContent The first content, top or start side
 * @param secondContent The second content, bottom or end side
 */
@Composable
fun AppBarLayout(
    modifier: Modifier = Modifier,
    layoutType: AppBarLayoutType = AppBarLayoutType.Column(),
    verticalScrollable: Boolean = true,
    contentPadding: PaddingValues = calculatePadding(),
    appBarStyle: TopAppBarStyles = TopAppBarStyles.Centered,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    firstContent: @Composable (CanonicalLayoutType) -> Unit,
    secondContent: @Composable (CanonicalLayoutType) -> Unit
) {
    when (layoutType) {
        is AppBarLayoutType.Column -> {
            ColumnAppBarLayout(
                modifier,
                layoutType,
                verticalScrollable,
                contentPadding,
                appBarStyle,
                title,
                navigationIcon,
                actions,
                firstContent,
                secondContent
            )
        }

        is AppBarLayoutType.Row -> {
            RowAppBarLayout(
                modifier,
                layoutType,
                contentPadding,
                appBarStyle,
                title,
                navigationIcon,
                actions,
                firstContent,
                secondContent
            )
        }

        is AppBarLayoutType.SizeAware -> {
            val doRow = LocalWindowSizeClass.current.widthSizeClass > WindowWidthSizeClass.Medium

            if (doRow) {
                RowAppBarLayout(
                    modifier,
                    layoutType,
                    contentPadding,
                    appBarStyle,
                    title,
                    navigationIcon,
                    actions,
                    firstContent,
                    secondContent
                )
            } else {
                ColumnAppBarLayout(
                    modifier,
                    layoutType,
                    verticalScrollable,
                    contentPadding,
                    appBarStyle,
                    title,
                    navigationIcon,
                    actions,
                    firstContent,
                    secondContent
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnAppBarLayout(
    modifier: Modifier = Modifier,
    layoutType: AppBarLayoutType,
    verticalScrollable: Boolean,
    paddingValues: PaddingValues,
    appBarStyle: TopAppBarStyles = TopAppBarStyles.Centered,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    firstContent: @Composable (CanonicalLayoutType) -> Unit,
    secondContent: @Composable (CanonicalLayoutType) -> Unit
) {
    Column(
        modifier = modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        MolAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            style = appBarStyle,
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            windowInsets = WindowInsets.statusBars
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val height = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .run {
                        if (verticalScrollable)
                            verticalScroll(rememberScrollState())
                        else this
                    },
                verticalArrangement = Arrangement.spacedBy(layoutType.spacing)
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(
                            min = layoutType.verticalFirstWeightRange!!.start * height,
                            max = layoutType.verticalFirstWeightRange!!.endInclusive * height
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    firstContent(CanonicalLayoutType.Column)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    secondContent(CanonicalLayoutType.Column)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowAppBarLayout(
    modifier: Modifier = Modifier,
    layoutType: AppBarLayoutType,
    paddingValues: PaddingValues,
    appBarStyle: TopAppBarStyles = TopAppBarStyles.Centered,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    firstContent: @Composable (CanonicalLayoutType) -> Unit,
    secondContent: @Composable (CanonicalLayoutType) -> Unit
) {
    Column(
        modifier = modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        MolAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            style = appBarStyle,
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            windowInsets = WindowInsets.statusBars
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = maxWidth

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalArrangement = Arrangement.spacedBy(layoutType.spacing)
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(
                            min = layoutType.horizontalFirstWeightRange!!.start * width,
                            max = layoutType.horizontalFirstWeightRange!!.endInclusive * width
                        )
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    firstContent(CanonicalLayoutType.Row)
                }
                Box(
                    modifier = Modifier
                        .widthIn(
                            min = (1 - layoutType.horizontalFirstWeightRange!!.endInclusive) * width,
                            max = (1 - layoutType.horizontalFirstWeightRange!!.start) * width
                        )
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    secondContent(CanonicalLayoutType.Row)
                }
            }
        }
    }
}