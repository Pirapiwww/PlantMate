package com.example.plantmate.ui.components.dropdowns

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalDropdown(
    value: Int?,                 // ⬅️ sekarang Int? (R.string id)
    placeholder: Int,             // ⬅️ placeholder juga R.string
    items: List<Int>,             // ⬅️ List<Int>
    onSelected: (Int) -> Unit,    // ⬅️ return R.string id
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = value?.let { context.getString(it) } ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(text = stringResource(id = placeholder))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { itemResId ->
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = itemResId))
                    },
                    onClick = {
                        onSelected(itemResId)
                        expanded = false
                    }
                )
            }
        }
    }
}
