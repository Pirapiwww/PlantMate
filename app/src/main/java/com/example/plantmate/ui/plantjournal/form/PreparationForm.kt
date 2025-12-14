package com.example.plantmate.ui.plantjournal.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.plantmate.R
import com.example.plantmate.data.DataSource
import com.example.plantmate.ui.components.dropdowns.JournalDropdown

@Composable
fun PreparationForm(
    plantName: String,
    plantType: String,
    plantSource: String,
    plantSoil: String,
    plantFertilizer: String,
    notes: String,
    onValueChange: (field: String, value: String) -> Unit
) {

    val ds = DataSource()

    // Plant Name
    Text(
        text = stringResource(R.string.plant_name),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = plantName,
        onValueChange = { onValueChange("plantName", it) },
        label = { Text(stringResource(R.string.plant_name)) },
        modifier = Modifier.fillMaxWidth()
    )

    // Plant Type
    Text(
        text = stringResource(R.string.plant_type),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.plant_type)),
        items = ds.loadPlantType(),
        onSelected = { onValueChange("plantType", it) }
    )

    // Plant Source
    Text(
        text = stringResource(R.string.source),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.source)),
        items = ds.loadSource(),
        onSelected = { onValueChange("plantSource", it) }
    )

    // Soil Type
    Text(
        text = stringResource(R.string.soil_type),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.soil_type)),
        items = ds.loadSoilType(),
        onSelected = { onValueChange("plantSoil", it) }
    )

    // Fertilizer Type
    Text(
        text = stringResource(R.string.fertilizer_type),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.fertilizer_type)),
        items = ds.loadFertilizerType(), // perbaikan
        onSelected = { onValueChange("plantFertilizer", it) }
    )

    // Notes
    Text(
        text = stringResource(R.string.note),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = notes,
        onValueChange = { onValueChange("notes", it) },
        label = { Text(stringResource(R.string.note)) },
        modifier = Modifier.fillMaxWidth(),
        minLines = 6
    )
}
