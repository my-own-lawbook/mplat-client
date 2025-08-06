package me.bumiller.mol.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.common.ui.input.inputValue
import me.bumiller.civoris.ui.Res
import me.bumiller.civoris.ui.cd_date_input_button
import me.bumiller.civoris.ui.date_input_dialog_cancel
import me.bumiller.civoris.ui.date_input_dialog_confirm
import org.jetbrains.compose.resources.stringResource

/**
 * Platform method to format a [LocalDate] into the version required by the [DateTextField].
 *
 * @return The formatted string.
 */
internal expect fun LocalDate.formatDateTextField(): String

/**
 * Readonly text field which display the date in a text field.
 *
 * @param modifier The modifier to apply
 * @param style The style of the text field
 * @param value The date value
 * @param label The label of the text field
 * @param onDateChanged The callback invoked when the date is changed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTextField(
    modifier: Modifier = Modifier,
    style: TextFieldStyle = TextFieldStyle.Filled,
    value: InputValue<LocalDate?> = inputValue(null),
    label: @Composable () -> Unit,
    onDateChanged: (LocalDate?) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }

    if (showPicker) {
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = value.value?.atStartOfDayIn(TimeZone.currentSystemDefault())
                ?.toEpochMilliseconds() ?: Clock.System.now().toEpochMilliseconds()
        )

        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val instant =
                            pickerState.selectedDateMillis?.let(Instant::fromEpochMilliseconds)
                        if (instant == null) {
                            onDateChanged(null)
                        } else {
                            val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
                            onDateChanged(date)
                        }

                        showPicker = false
                    }
                ) {
                    Text(stringResource(Res.string.date_input_dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPicker = !showPicker }
                ) {
                    Text(stringResource(Res.string.date_input_dialog_cancel))
                }
            }
        ) {
            DatePicker(pickerState)
        }
    }

    CivorisTextField(
        modifier = modifier,
        value = value.map {
            it?.formatDateTextField() ?: ""
        },
        onValueChange = {
            // Text field is read-only.
        },
        style = style,
        label = label,
        readOnly = true,
        trailingIcon = {
            IconButton(
                onClick = { showPicker = !showPicker },
                enabled = value.canEdit
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = stringResource(Res.string.cd_date_input_button)
                )
            }
        }
    )
}