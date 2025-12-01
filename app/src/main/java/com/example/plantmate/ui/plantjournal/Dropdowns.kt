package com.example.plantmate.ui.plantjournal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCategoryDropdown() {
    var category by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var customCategory by remember { mutableStateOf("") }

    val categories = listOf("Ornamental", "Vegetable", "Fruit", "Other")

    Column(modifier = Modifier.fillMaxWidth()) {

        // Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Plant Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            category = item
                            expanded = false
                            if (item != "Other") customCategory = "" // reset if not Other
                        }
                    )
                }
            }
        }

        // If user selects "Other", show additional input field
        if (category == "Other") {
            OutlinedTextField(
                value = customCategory,
                onValueChange = { customCategory = it },
                label = { Text("Specify Category") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrowingMediaDropdown() {
    var media by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var customMedia by remember { mutableStateOf("") }

    // Main categories
    val mediaCategories = listOf("Soil", "Container Media", "Other")

    Column(modifier = Modifier.fillMaxWidth()) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = media,
                onValueChange = {},
                readOnly = true,
                label = { Text("Growing Media") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                mediaCategories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            media = item
                            expanded = false
                            if (item != "Other") customMedia = "" // reset jika bukan Other
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Additional input if user selects "Other"
        if (media == "Other") {
            OutlinedTextField(
                value = customMedia,
                onValueChange = { customMedia = it },
                label = { Text("Specify Media") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

