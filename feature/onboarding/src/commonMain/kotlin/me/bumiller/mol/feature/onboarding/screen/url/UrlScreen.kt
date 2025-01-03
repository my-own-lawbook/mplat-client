package me.bumiller.mol.feature.onboarding.screen.url

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import me.bumiller.mol.onboarding.Res
import me.bumiller.mol.onboarding.cd_url_screen
import me.bumiller.mol.onboarding.url_screen
import me.bumiller.mol.onboarding.url_screen_button_confirm_label
import me.bumiller.mol.onboarding.url_screen_description
import me.bumiller.mol.onboarding.url_screen_input_label
import me.bumiller.mol.onboarding.url_screen_title
import me.bumiller.mol.ui.components.TextFieldStyle
import me.bumiller.mol.ui.components.UrlTextField
import me.bumiller.mol.ui.layout.AppBarLayout
import me.bumiller.mol.ui.layout.AppBarLayoutType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * The onboarding screen that allows the user to enter/change the url of the backend they are connecting to.
 *
 * @param onUrlSet The callback invoked when the url was successfully set.
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun UrlScreen(
    onUrlSet: () -> Unit
) {
    val viewModel = koinViewModel<UrlViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                UrlEvent.Continue -> onUrlSet()
            }
        }
    }

    val formState by viewModel.formState.collectAsStateWithLifecycle()

    UrlScreen(formState, viewModel::onEvent)
}

@Composable
private fun UrlScreen(
    formState: UrlState,
    onEvent: (UrlUiEvent) -> Unit
) {
    AppBarLayout(
        modifier = Modifier
            .fillMaxSize(),
        layoutType = AppBarLayoutType.SizeAware(
            verticalFirstWeightRange = 0.1F..0.2F,
            horizontalFirstWeightRange = 0.3F..0.4F,
            spacing = 64.dp
        ),
        title = {
            Text(stringResource(Res.string.url_screen_title))
        },
        firstContent = {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
                painter = painterResource(Res.drawable.url_screen),
                contentDescription = stringResource(Res.string.cd_url_screen)
            )
        },
        secondContent = { type ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 32.dp,
                    alignment = if (type.isVertical) Alignment.Top
                    else Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.url_screen_description),
                    textAlign = TextAlign.Center
                )

                UrlTextField(
                    value = formState.url,
                    onValueChange = { onEvent(UrlUiEvent.ChangeUrl(it)) },
                    style = TextFieldStyle.Outlined,
                    label = { Text(stringResource(Res.string.url_screen_input_label)) }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onEvent(UrlUiEvent.Confirm) }
                ) {
                    Text(stringResource(Res.string.url_screen_button_confirm_label))
                }
            }
        }
    )
}