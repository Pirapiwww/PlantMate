package com.example.plantmate.ui.myjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PlantingLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PreparationLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.TreatmentLocalViewModel
import com.example.plantmate.ui.components.JournalCardReadOnly
import com.example.plantmate.ui.plantjournal.ExpandableCard
import com.example.plantmate.ui.plantjournal.content.PlantingContent
import com.example.plantmate.ui.plantjournal.content.PreparationContent
import com.example.plantmate.ui.plantjournal.content.TreatmentContent

@Composable
fun JournalCategoryResultScreen(
    categoryId: Int,
    type: String,
    navController: NavHostController
) {
    val app = LocalContext.current.applicationContext as YourApp

    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)
    val plantingViewModel: PlantingLocalViewModel =
        viewModel(factory = app.viewModelFactory)
    val preparationViewModel: PreparationLocalViewModel =
        viewModel(factory = app.viewModelFactory)
    val treatmentViewModel: TreatmentLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val journal by journalViewModel.selectedJournal.collectAsState()
    val preparation by preparationViewModel.selectedPreparation.collectAsState()
    val planting by plantingViewModel.selectedPlanting.collectAsState()
    val treatment by treatmentViewModel.selectedTreatment.collectAsState()

    var expanded by rememberSaveable { mutableStateOf(false) }

    /* ================= LOAD FORM ENTITY ================= */
    LaunchedEffect(categoryId, type) {
        when (type) {
            "preparation" -> preparationViewModel.loadById(categoryId)
            "planting" -> plantingViewModel.loadById(categoryId)
            "treatment" -> treatmentViewModel.loadById(categoryId)
        }
    }

    /* ================= LOAD JOURNAL ================= */
    LaunchedEffect(preparation, planting, treatment) {
        val journalId =
            preparation?.journalId
                ?: planting?.journalId
                ?: treatment?.journalId

        journalId?.let {
            journalViewModel.loadJournalById(it)
        }
    }

    /* ================= ANALYSIS AI FROM ROOM ================= */
    val analysisAI =
        preparation?.analysisAI
            ?: planting?.analysisAI
            ?: treatment?.analysisAI
            ?: "-"

    /* ================= JOURNAL ID FROM ROOM ================= */
    val journalId =
        preparation?.journalId
            ?: planting?.journalId
            ?: treatment?.journalId
            ?: "-"

    /* ================= ROOT ================= */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        /* ================= TOP BAR ================= */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigate("myjournal/journal/$journalId") {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = stringResource(id = R.string.my_journal),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        /* ================= SCROLLABLE CONTENT ================= */
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            journal?.let {
                JournalCardReadOnly(item = it)
            }

            val details = when (type) {
                "preparation" -> stringResource(R.string.preparation_detail)
                "planting" -> stringResource(R.string.planting_detail)
                "treatment" -> stringResource(R.string.treatment_detail)
                else -> ""
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExpandableCard(
                title = details,
                expanded = expanded,
                onClick = { expanded = !expanded }
            ) {
                when (type) {
                    "preparation" -> preparation?.let { PreparationContent(it) }
                    "planting" -> planting?.let { PlantingContent(it) }
                    "treatment" -> treatment?.let { TreatmentContent(it) }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.lens_result),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = analysisAI.ifBlank { "-" },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
