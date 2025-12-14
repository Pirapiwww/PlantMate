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
fun PlantingForm(
    plantName: String,
    method: String,
    location: String,
    frequency: String,
    amount: String,
    notes: String,
    onValueChange: (String, String) -> Unit
) {

    val ds = DataSource()

    // PLANT NAME
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

    // METHOD
    Text(
        text = stringResource(R.string.method),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.method)),
        items = ds.loadMethod(),
        onSelected = { onValueChange("method", it) }
    )

    // LOCATION
    Text(
        text = stringResource(R.string.location),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.location)),
        items = ds.loadLocation(),
        onSelected = { onValueChange("location", it) }
    )

    // FREQUENCY
    Text(
        text = stringResource(R.string.frequency),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.frequency)),
        items = ds.loadFrequency(),
        onSelected = { onValueChange("frequency", it) }
    )

    // AMOUNT
    Text(
        text = stringResource(R.string.amount),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.amount)),
        items = ds.loadAmount(),
        onSelected = { onValueChange("amount", it) }
    )

    // NOTES
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
