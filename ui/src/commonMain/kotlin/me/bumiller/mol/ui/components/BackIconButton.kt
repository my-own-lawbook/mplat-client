package me.bumiller.mol.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.bumiller.mol.ui.Res
import me.bumiller.mol.ui.cd_back_button
import org.jetbrains.compose.resources.stringResource

/**
 * Helper function encapsulating the default back button for app bars.
 *
 * @param modifier The modifier to apply
 * @param onBack The callback invoked when the button is clicked
 */
@Composable
fun BackIconButton(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onBack
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = stringResource(Res.string.cd_back_button)
        )
    }
}