package me.bumiller.mol.feature.auth.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.bumiller.mol.auth.Res
import me.bumiller.mol.auth.profile_screen_confirm_button_label
import me.bumiller.mol.auth.profile_screen_description
import me.bumiller.mol.auth.profile_screen_display
import me.bumiller.mol.auth.profile_screen_input_birthday_label
import me.bumiller.mol.auth.profile_screen_input_first_name_label
import me.bumiller.mol.auth.profile_screen_input_gender_label
import me.bumiller.mol.auth.profile_screen_input_last_name_label
import me.bumiller.mol.auth.profile_screen_title
import me.bumiller.mol.common.ui.localization.localizedName
import me.bumiller.mol.common.ui.viewmodel.ViewModelScope
import me.bumiller.mol.feature.auth.model.SignupStage
import me.bumiller.mol.feature.auth.screen.SignupStageEvent
import me.bumiller.mol.model.user.Gender
import me.bumiller.mol.ui.components.DateTextField
import me.bumiller.mol.ui.components.DropdownTextField
import me.bumiller.mol.ui.components.CivorisTextField
import me.bumiller.mol.ui.components.TextFieldStyle
import me.bumiller.mol.ui.components.WideButton
import me.bumiller.mol.ui.layout.AppBarLayoutWithDisplay
import org.jetbrains.compose.resources.stringResource

/**
 * Composable for the profile screen.
 *
 * @param onStageChange Callback for when the signup stage changes
 */
@Composable
internal fun ProfileScreen(
    onStageChange: (SignupStage) -> Unit
) {
    ViewModelScope<ProfileUiEvent, SignupStageEvent, ProfileViewModel>(
        onViewModelEvent = { event ->
            when (event) {
                is SignupStageEvent.SignupStageChanged -> onStageChange(event.stage)
            }
        }
    ) { vm ->
        val formState by vm.formState.collectAsStateWithLifecycle()

        ProfileScreen(vm::onEvent, formState)
    }
}

@Composable
private fun ProfileScreen(
    onEvent: (ProfileUiEvent) -> Unit,
    formState: ProfileUiState
) {
    AppBarLayoutWithDisplay(
        modifier = Modifier
            .fillMaxSize(),
        title = {
            Text(stringResource(Res.string.profile_screen_title))
        },
        description = {
            Text(stringResource(Res.string.profile_screen_description))
        },
        display = {
            Text(stringResource(Res.string.profile_screen_display))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CivorisTextField(
                value = formState.firstName,
                onValueChange = { onEvent(ProfileUiEvent.ChangeFirstName(it)) },
                style = TextFieldStyle.Outlined,
                label = { Text(stringResource(Res.string.profile_screen_input_first_name_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            CivorisTextField(
                value = formState.lastName,
                onValueChange = { onEvent(ProfileUiEvent.ChangeLastName(it)) },
                style = TextFieldStyle.Outlined,
                label = { Text(stringResource(Res.string.profile_screen_input_last_name_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            DateTextField(
                value = formState.birthday,
                label = {
                    Text(stringResource(Res.string.profile_screen_input_birthday_label))
                },
                onDateChanged = {
                    onEvent(ProfileUiEvent.ChangeBirthday(it))
                },
                style = TextFieldStyle.Outlined
            )

            DropdownTextField(
                value = formState.gender,
                label = {
                    Text(stringResource(Res.string.profile_screen_input_gender_label))
                },
                values = Gender.values(),
                formatValue = { it.localizedName() },
                onSelect = {
                    onEvent(ProfileUiEvent.ChangeGender(it))
                }
            )

            WideButton(
                onClick = { onEvent(ProfileUiEvent.Confirm) }
            ) {
                Text(stringResource(Res.string.profile_screen_confirm_button_label))
            }
        }
    }
}