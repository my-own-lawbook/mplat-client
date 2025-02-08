package me.bumiller.mol.feature.onboarding.screen.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.bumiller.mol.common.ui.input.inputValue
import me.bumiller.mol.common.ui.localization.localizedName
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.model.settings.ColorMode
import me.bumiller.mol.model.settings.ColorScheme
import me.bumiller.mol.model.settings.ColorSchemeContrastLevel
import me.bumiller.mol.model.settings.UserSettings
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.onboarding.Res
import me.bumiller.mol.onboarding.cd_design_screen
import me.bumiller.mol.onboarding.design_screen
import me.bumiller.mol.onboarding.design_screen_button_continue_label
import me.bumiller.mol.onboarding.design_screen_description
import me.bumiller.mol.onboarding.design_screen_input_contrastlevel_label
import me.bumiller.mol.onboarding.design_screen_input_mode_label
import me.bumiller.mol.onboarding.design_screen_input_scheme_label
import me.bumiller.mol.onboarding.design_screen_title
import me.bumiller.mol.ui.components.BackIconButton
import me.bumiller.mol.ui.components.DropdownTextField
import me.bumiller.mol.ui.layout.AppBarLayoutWithImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Screen that lets the user adjust several settings related to the design and visuals of the app.
 *
 * @param onFinish Callback when the user pressed the finish button
 * @param onBack Callback when the user clicked the back button
 */
@Composable
internal fun DesignScreen(
    onFinish: () -> Unit,
    onBack: () -> Unit
) {
    ViewModelScope<DesignUiEvent, DesignEvent, DesignViewModel>(
        onViewModelEvent = { event ->
            when (event) {
                DesignEvent.Continue -> onFinish()
                DesignEvent.Return -> onBack()
            }
        }
    ) { vm ->
        val settings by vm.settings.collectAsStateWithLifecycle()

        if (settings is SimpleState.Success) {
            DesignScreen(settings.dataOrNull()!!, vm::onEvent)
        }
    }
}

@Composable
private fun DesignScreen(
    settings: UserSettings,
    onEvent: (DesignUiEvent) -> Unit
) {
    AppBarLayoutWithImage(
        modifier = Modifier
            .fillMaxSize(),
        navigationIcon = {
            BackIconButton {
                onEvent(DesignUiEvent.Return)
            }
        },
        title = {
            Text(stringResource(Res.string.design_screen_title))
        },
        imagePainter = painterResource(Res.drawable.design_screen),
        imageContentDescription = stringResource(Res.string.cd_design_screen),
        description = {
            Text(stringResource(Res.string.design_screen_description))
        }
    ) { layoutType ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                space = 32.dp,
                alignment = if (layoutType.isVertical) Alignment.Top
                else Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropdownTextField(
                value = inputValue(settings.colorMode),
                label = {
                    Text(stringResource(Res.string.design_screen_input_mode_label))
                },
                values = ColorMode.entries,
                formatValue = { it.localizedName() },
                onSelect = { onEvent(DesignUiEvent.ChangeMode(it)) }
            )

            DropdownTextField(
                value = inputValue(settings.colorScheme),
                label = {
                    Text(stringResource(Res.string.design_screen_input_scheme_label))
                },
                values = ColorScheme.entries,
                formatValue = { it.localizedName() },
                onSelect = { onEvent(DesignUiEvent.ChangeScheme(it)) }
            )

            Column {
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    text = stringResource(Res.string.design_screen_input_contrastlevel_label),
                    style = MaterialTheme.typography.labelLarge
                )
                Slider(
                    value = when (settings.contrastLevel) {
                        ColorSchemeContrastLevel.Normal -> 0F
                        ColorSchemeContrastLevel.Medium -> 1F
                        ColorSchemeContrastLevel.High -> 2F
                    },
                    enabled = settings.colorScheme == ColorScheme.App,
                    onValueChange = {
                        val level = when (it) {
                            1F -> ColorSchemeContrastLevel.Medium
                            2F -> ColorSchemeContrastLevel.High
                            else -> ColorSchemeContrastLevel.Normal
                        }
                        onEvent(DesignUiEvent.ChangeContrastLevel(level))
                    },
                    steps = 1,
                    valueRange = 0F..2F
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onEvent(DesignUiEvent.Continue) }
                ) {
                    Text(stringResource(Res.string.design_screen_button_continue_label))
                }
            }
        }
    }
}