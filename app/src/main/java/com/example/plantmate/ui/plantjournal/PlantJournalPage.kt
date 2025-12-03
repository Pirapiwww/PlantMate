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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.ui.plantjournal.form.PlantingForm
import com.example.plantmate.ui.plantjournal.form.PreparationForm
import com.example.plantmate.ui.plantjournal.form.TreatmentForm


@Composable
fun PlantJournalScreen(
    onBack: () -> Unit
) {

    var selectedStage by remember { mutableStateOf("Planting") }
    val stageOptions = listOf("Preparation", "Planting", "Treatment")


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
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }

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
                "Preparation" -> PreparationForm()
                "Planting" -> PlantingForm()
                "Treatment" -> TreatmentForm()
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

