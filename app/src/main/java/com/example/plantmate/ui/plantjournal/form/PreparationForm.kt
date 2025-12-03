package com.example.plantmate.ui.plantjournal.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.plantmate.R
import com.example.plantmate.ui.components.DatePicker
import com.example.plantmate.ui.components.GrowingMediaDropdown
import com.example.plantmate.ui.components.PlantCategoryDropdown

@Composable
fun PreparationForm() {

    var date by remember { mutableStateOf("") }
    var plantName by remember { mutableStateOf("") }
    var plantType by remember { mutableStateOf("") }
    var media by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Text(
        stringResource(id = R.string.plant_name),
        modifier = Modifier.Companion.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = plantName,
        onValueChange = { plantName = it },
        label = { Text("Plant Name") },
        modifier = Modifier.Companion.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.preparation_date),
        modifier = Modifier.Companion.padding(top = 16.dp, bottom = 6.dp)
    )

    DatePicker()

    Text(
        stringResource(id = R.string.plant_type),
        modifier = Modifier.Companion.padding(top = 16.dp, bottom = 6.dp)
    )

    PlantCategoryDropdown()

    Text(
        stringResource(id = R.string.growing_media),
        modifier = Modifier.Companion.padding(top = 16.dp, bottom = 6.dp)
    )

    GrowingMediaDropdown()

    Text(
        stringResource(id = R.string.note),
        modifier = Modifier.Companion.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = notes,
        onValueChange = { notes = it },
        label = { Text(stringResource(id = R.string.note)) },
        modifier = Modifier.Companion.fillMaxWidth(),
        minLines = 6
    )
}