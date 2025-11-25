package com.example.plantmate.ui.plantjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantmate.R


@Composable
fun PlantJournalScreen() {

    var selectedStage by remember { mutableStateOf("Persiapan") }
    val stageOptions = listOf("Persiapan", "Penanaman", "Perawatan")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // ============================
        //          TOP BAR
        // ============================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                stringResource(id = R.string.plant_journal),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp)) // menjaga text tetap center
        }


        // ============================
        //  KONTEN (Boleh discroll)
        // ============================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            StageDropdown(
                selected = selectedStage,
                options = stageOptions,
                onSelected = { selectedStage = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedStage) {
                "Persiapan" -> PreparationForm()
                "Penanaman" -> PlantingForm()
                "Perawatan" -> CareForm()
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* TODO Save */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Journal")
            }
        }
    }
}



@Composable
fun StageDropdown(selected: String, options: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            stringResource(id = R.string.phase),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selected)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onSelected(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PreparationForm() {

    var date by remember { mutableStateOf("") }
    var plantName by remember { mutableStateOf("") }
    var plantType by remember { mutableStateOf("") }
    var media by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Text(
        stringResource(id = R.string.preparation_date),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = date,
        onValueChange = { date = it },
        label = { Text("Tanggal Persiapan") },
        modifier = Modifier
            .fillMaxWidth()
    )

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
        stringResource(id = R.string.plant_type),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    OutlinedTextField(
        value = plantType,
        onValueChange = { plantType = it },
        label = { Text("Jenis Tanaman") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.growing_media),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = media,
        onValueChange = { media = it },
        label = { Text("Media Tanam") },
        modifier = Modifier.fillMaxWidth()
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

@Composable
fun PlantingForm() {

    var seedCount by remember { mutableStateOf("") }
    var plantingDate by remember { mutableStateOf("") }
    var method by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Text(
        stringResource(id = R.string.seeds),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = seedCount,
        onValueChange = { seedCount = it },
        label = { Text("Jumlah Bibit") },
        modifier = Modifier.fillMaxWidth()
    )

    Text(
        stringResource(id = R.string.planting_date),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = plantingDate,
        onValueChange = { plantingDate = it },
        label = { Text("Tanggal Tanam") },
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
        modifier = Modifier.fillMaxWidth()
    )

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

@Composable
fun CareForm() {

    var plantCondition by remember { mutableStateOf("") }
    var noteDate by remember { mutableStateOf("") }
    var watering by remember { mutableStateOf("") }
    var fertilizer by remember { mutableStateOf("") }
    var issue by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlantJournalScreenPreview() {
    PlantJournalScreen()
}
