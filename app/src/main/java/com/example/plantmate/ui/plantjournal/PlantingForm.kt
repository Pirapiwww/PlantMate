package com.example.plantmate.ui.plantjournal

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

@Composable
fun PlantingForm() {

    var seedCount by remember { mutableStateOf("") }
    var plantName by remember { mutableStateOf("") }
    var plantingDate by remember { mutableStateOf("") }
    var method by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Text(
        stringResource(id = R.string.plant_name),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = plantName,
        onValueChange = { plantName = it },
        label = { Text("Nama Tanaman") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.planting_date),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    DatePicker()

    Text(
        stringResource(id = R.string.initial_conditions),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = condition,
        onValueChange = { condition = it },
        label = { Text("Kondisi Awal Tanaman") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.seeds),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = seedCount,
        onValueChange = { seedCount = it },
        label = { Text("0 if not using seeds") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.how_to_plant),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = method,
        onValueChange = { method = it },
        label = { Text("Cara Menanam") },
        modifier = Modifier
            .fillMaxWidth(),
        minLines = 4
    )

    Text(
        stringResource(id = R.string.note),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = notes,
        onValueChange = { notes = it },
        label = { Text("Catatan Tambahan") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 6
    )
}