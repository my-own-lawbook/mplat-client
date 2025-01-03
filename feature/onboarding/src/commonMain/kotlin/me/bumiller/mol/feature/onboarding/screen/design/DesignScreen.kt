package me.bumiller.mol.feature.onboarding.screen.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.common.ui.localization.localizedName
import me.bumiller.mol.model.ColorMode
import me.bumiller.mol.model.ColorScheme
import me.bumiller.mol.model.ColorSchemeContrastLevel
import me.bumiller.mol.model.UserSettings
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
import me.bumiller.mol.ui.layout.AppBarLayout
import me.bumiller.mol.ui.layout.AppBarLayoutType
import me.bumiller.mol.ui.locals.LocalWindowSizeClass
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Screen that lets the user adjust several settings related to the design and visuals of the app.
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun DesignScreen() {
    val viewModel = koinViewModel<DesignViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                DesignEvent.Continue -> TODO()
                DesignEvent.Return -> TODO()
            }
        }
    }

    val settings by viewModel.settings.collectAsStateWithLifecycle()

    if (settings.isSuccess) {
        DesignScreen(settings.dataOrNull()!!, viewModel::onEvent)
    }
}

@Composable
private fun DesignScreen(
    settings: UserSettings,
    onEvent: (DesignUiEvent) -> Unit
) {
    val windowHeightSizeClass = LocalWindowSizeClass.current.heightSizeClass
    val includeImageVertical = windowHeightSizeClass >= WindowHeightSizeClass.Expanded

    val verticalRange = if (includeImageVertical) 0.2F..0.3F
    else 0.1F..0.4F

    AppBarLayout(
        modifier = Modifier
            .fillMaxSize(),
        layoutType = AppBarLayoutType.SizeAware(
            spacing = 32.dp,
            verticalFirstWeightRange = verticalRange,
            horizontalFirstWeightRange = 0.3F..0.4F
        ),
        navigationIcon = {
            BackIconButton {
                onEvent(DesignUiEvent.Return)
            }
        },
        title = {
            Text(stringResource(Res.string.design_screen_title))
        },
        firstContent = { layoutType ->
            Column {
                if (!layoutType.isVertical || includeImageVertical) {
                    val modifier = if (layoutType.isVertical)
                        Modifier
                            .weight(1F)
                    else
                        Modifier
                            .heightIn(max = 300.dp)
                    Image(
                        modifier = modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        painter = painterResource(Res.drawable.design_screen),
                        contentDescription = stringResource(Res.string.cd_design_screen)
                    )
                }

                Text(stringResource(Res.string.design_screen_description))
            }
        },
        secondContent = { layoutType ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 32.dp,
                    alignment = if (layoutType.isVertical) Alignment.Top
                    else Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DropdownTextField(
                    value = InputValue(settings.colorMode),
                    label = {
                        Text(stringResource(Res.string.design_screen_input_mode_label))
                    },
                    values = ColorMode.entries,
                    formatValue = { it.localizedName() },
                    onSelect = { onEvent(DesignUiEvent.ChangeMode(it)) }
                )

                DropdownTextField(
                    value = InputValue(settings.colorScheme),
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
    )
}