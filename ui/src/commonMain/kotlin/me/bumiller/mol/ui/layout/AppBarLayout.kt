package me.bumiller.mol.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import me.bumiller.mol.ui.components.MolAppBar
import me.bumiller.mol.ui.components.TopAppBarStyles
import me.bumiller.mol.ui.locals.LocalWindowSizeClass

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
 * Layout that combines two chunks of content with an app bar.
 *
 * @param modifier The modifier
 * @param layoutType The layout type that defines how the two content chunks will be combined
 * @param appBarStyle The style of the app bar
 * @param title The title
 * @param navigationIcon The navigation icon
 * @param actions The actions
 * @param windowInsets The window insets
 * @param firstContent The first content, top or start side
 * @param secondContent The second content, bottom or end side
 */
@Composable
fun AppBarLayout(
    modifier: Modifier = Modifier,
    layoutType: AppBarLayoutType = AppBarLayoutType.Column(),
    appBarStyle: TopAppBarStyles = TopAppBarStyles.Centered,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit
) {
    when (layoutType) {
        is AppBarLayoutType.Column -> {
            ColumnAppBarLayout(
                modifier,
                layoutType,
                appBarStyle,
                title,
                navigationIcon,
                actions,
                windowInsets,
                firstContent,
                secondContent
            )
        }

        is AppBarLayoutType.Row -> {
            RowAppBarLayout(
                modifier,
                layoutType,
                appBarStyle,
                title,
                navigationIcon,
                actions,
                windowInsets,
                firstContent,
                secondContent
            )
        }

        is AppBarLayoutType.SizeAware -> {
            val doRow = LocalWindowSizeClass.current.widthSizeClass >= WindowWidthSizeClass.Medium

            if (doRow) {
                ColumnAppBarLayout(
                    modifier,
                    layoutType,
                    appBarStyle,
                    title,
                    navigationIcon,
                    actions,
                    windowInsets,
                    firstContent,
                    secondContent
                )
            } else {
                RowAppBarLayout(
                    modifier,
                    layoutType,
                    appBarStyle,
                    title,
                    navigationIcon,
                    actions,
                    windowInsets,
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
    appBarStyle: TopAppBarStyles = TopAppBarStyles.Centered,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        MolAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            style = appBarStyle,
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            windowInsets = windowInsets
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val height = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(
                            min = layoutType.verticalFirstWeightRange!!.start * height,
                            max = layoutType.verticalFirstWeightRange!!.endInclusive * height
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    firstContent()
                }
                Box(
                    modifier = Modifier
                        .heightIn(
                            min = (1 - layoutType.verticalFirstWeightRange!!.endInclusive) * height,
                            max = (1 - layoutType.verticalFirstWeightRange!!.start) * height
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    secondContent()
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
    appBarStyle: TopAppBarStyles = TopAppBarStyles.Centered,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        MolAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            style = appBarStyle,
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            windowInsets = windowInsets
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = maxWidth

            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(
                            min = layoutType.horizontalFirstWeightRange!!.start * width,
                            max = layoutType.horizontalFirstWeightRange!!.endInclusive * width
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    firstContent()
                }
                Box(
                    modifier = Modifier
                        .widthIn(
                            min = (1 - layoutType.horizontalFirstWeightRange!!.endInclusive) * width,
                            max = (1 - layoutType.horizontalFirstWeightRange!!.start) * width
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    secondContent()
                }
            }
        }
    }
}