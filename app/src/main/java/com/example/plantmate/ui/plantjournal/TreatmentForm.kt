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
fun TreatmentForm() {

    var plantName by remember { mutableStateOf("") }
    var plantCondition by remember { mutableStateOf("") }
    var noteDate by remember { mutableStateOf("") }
    var watering by remember { mutableStateOf("") }
    var fertilizer by remember { mutableStateOf("") }
    var issue by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }
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
        stringResource(id = R.string.treatment_date),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = noteDate,
        onValueChange = { noteDate = it },
        label = { Text("Tanggal Catatan") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.plant_condition),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = plantCondition,
        onValueChange = { plantCondition = it },
        label = { Text("Kondisi Tanaman") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.watering),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = watering,
        onValueChange = { watering = it },
        label = { Text("Penyiraman") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.fertilizer),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = fertilizer,
        onValueChange = { fertilizer = it },
        label = { Text("Pupuk") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.problem_details),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = issue,
        onValueChange = { issue = it },
        label = { Text("Masalah / Gejala") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 4
    )

    Text(
        stringResource(id = R.string.solution_details),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = solution,
        onValueChange = { solution = it },
        label = { Text("Tindakan / Solusi") },
        modifier = Modifier.fillMaxWidth(),
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