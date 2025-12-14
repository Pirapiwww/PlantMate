package com.example.plantmate.ui.plantjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.plantmate.ui.plantjournal.form.PlantingForm
import com.example.plantmate.ui.plantjournal.form.PreparationForm
import com.example.plantmate.ui.plantjournal.form.TreatmentForm

@Composable
fun PlantJournalScreen(
    onBack: () -> Unit = {}
) {

    var selectedTab by remember { mutableStateOf(0) }

    // ---------- STATES ----------
    var prepPlantName by remember { mutableStateOf("") }
    var prepPlantType by remember { mutableStateOf("") }
    var prepSource by remember { mutableStateOf("") }
    var prepSoil by remember { mutableStateOf("") }
    var prepFertilizer by remember { mutableStateOf("") }
    var prepNotes by remember { mutableStateOf("") }

    var plantName by remember { mutableStateOf("") }
    var method by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var plantNotes by remember { mutableStateOf("") }

    var treatName by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var treatmentType by remember { mutableStateOf("") }
    var problem by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }
    var treatNotes by remember { mutableStateOf("") }

    val onValueChange: (String, String) -> Unit = { field, value ->
        when (field) {
            "prepPlantName" -> prepPlantName = value
            "prepPlantType" -> prepPlantType = value
            "prepSource" -> prepSource = value
            "prepSoil" -> prepSoil = value
            "prepFertilizer" -> prepFertilizer = value
            "prepNotes" -> prepNotes = value

            "plantName" -> plantName = value
            "method" -> method = value
            "location" -> location = value
            "frequency" -> frequency = value
            "amount" -> amount = value
            "plantNotes" -> plantNotes = value

            "treatName" -> treatName = value
            "condition" -> condition = value
            "treatmentType" -> treatmentType = value
            "problem" -> problem = value
            "solution" -> solution = value
            "treatNotes" -> treatNotes = value
        }
    }

    val topColor = Color(0xFFDDE6C7)
    val activeColor = Color(0xFF6AA85B)      // hijau
    val inactiveColor = Color.Black          // hitam

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ================================
        //          TOP BAR
        // ================================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(topColor)
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
                text = "Plant Journal",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        // ================================
        //            TAB ROW
        // ================================
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = activeColor,

            // Hilangkan garis bawah bawaan TabRow
            divider = {},

            // Indicator custom
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTab]),
                    color = activeColor,
                    height = 3.dp
                )
            }
        ) {

            val tabs = listOf("Preparation", "Planting", "Treatment")

            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTab == index) activeColor else inactiveColor
                        )
                    },
                    // Tidak ada garis bawah tambahan di sini
                    selectedContentColor = activeColor,
                    unselectedContentColor = inactiveColor
                )
            }
        }

        // ================================
        //        SCROLLABLE CONTENT
        // ================================
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .weight(1f)
        ) {

            when (selectedTab) {

                0 -> PreparationForm(
                    plantName = prepPlantName,
                    plantType = prepPlantType,
                    plantSource = prepSource,
                    plantSoil = prepSoil,
                    plantFertilizer = prepFertilizer,
                    notes = prepNotes,
                    onValueChange = onValueChange
                )

                1 -> PlantingForm(
                    plantName = plantName,
                    method = method,
                    location = location,
                    frequency = frequency,
                    amount = amount,
                    notes = plantNotes,
                    onValueChange = onValueChange
                )

                2 -> TreatmentForm(
                    plantName = treatName,
                    plantCondition = condition,
                    treatmentType = treatmentType,
                    problem = problem,
                    solution = solution,
                    notes = treatNotes,
                    onValueChange = onValueChange
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO submit */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Journal")
            }
        }
    }
}
