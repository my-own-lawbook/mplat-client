package me.bumiller.mol.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * Different variations of the app bar.
 */
enum class TopAppBarStyles {

    /**
     * Normal size.
     */
    Normal,

    /**
     * Medium size.
     */
    Medium,

    /**
     * Large size.
     */
    Large,

    /**
     * Centered size.
     */
    Centered

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MolAppBar(
    modifier: Modifier = Modifier,
    style: TopAppBarStyles = TopAppBarStyles.Centered,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    collapsedHeight: Dp = TopAppBarDefaults.MediumAppBarCollapsedHeight,
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = WindowInsets(0, 0, 0, 0),
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    when (style) {
        TopAppBarStyles.Normal -> {
            TopAppBar(
                title,
                modifier,
                navigationIcon,
                actions,
                expandedHeight,
                windowInsets,
                colors,
                scrollBehavior
            )
        }

        TopAppBarStyles.Medium -> {
            MediumTopAppBar(
                title,
                modifier,
                navigationIcon,
                actions,
                expandedHeight,
                collapsedHeight,
                windowInsets,
                colors,
                scrollBehavior
            )
        }

        TopAppBarStyles.Large -> {
            LargeTopAppBar(
                title,
                modifier,
                navigationIcon,
                actions,
                expandedHeight,
                collapsedHeight,
                windowInsets,
                colors,
                scrollBehavior
            )
        }

        TopAppBarStyles.Centered -> {
            CenterAlignedTopAppBar(
                title,
                modifier,
                navigationIcon,
                actions,
                expandedHeight,
                windowInsets,
                colors,
                scrollBehavior
            )
        }
    }
}