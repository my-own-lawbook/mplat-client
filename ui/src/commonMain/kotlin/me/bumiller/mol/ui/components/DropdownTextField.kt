package me.bumiller.mol.ui.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import me.bumiller.mol.common.ui.input.InputValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <Item> DropdownTextField(
    modifier: Modifier = Modifier,
    value: InputValue<Item>,
    label: (@Composable () -> Unit)? = null,
    values: List<Item>,
    formatValue: @Composable (Item) -> String,
    onSelect: (Item) -> Unit
) {
    val nameMap = values.associateWith { formatValue(it) }

    var exposed by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = exposed,
        onExpandedChange = {
            exposed = it
        }
    ) {
        MolTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable),
            value = value.map { nameMap[it]!! },
            onValueChange = { },
            label = label,
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = exposed,
            onDismissRequest = {
                exposed = false
            }
        ) {
            values.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        onSelect(value)
                        exposed = false
                    },
                    text = { Text(formatValue(value)) }
                )
            }
        }
    }
}