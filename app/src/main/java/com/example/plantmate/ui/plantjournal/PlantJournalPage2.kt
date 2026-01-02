package com.example.plantmate.ui.plantjournal

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.viewmodel.AnalysisViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.*
import com.example.plantmate.ui.components.JournalCardReadOnly
import com.example.plantmate.ui.plantjournal.content.*
import kotlinx.coroutines.launch

@Composable
fun PlantJournalResultScreen(
    categoryId: Int,
    type: String,
    navController: NavHostController
) {
    val app = LocalContext.current.applicationContext as YourApp
    val scope = rememberCoroutineScope()

    /* ================= VIEWMODELS ================= */

    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val plantingViewModel: PlantingLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val preparationViewModel: PreparationLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val treatmentViewModel: TreatmentLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val analysisViewModel: AnalysisViewModel = viewModel()

    /* ================= STATES ================= */

    val journal by journalViewModel.selectedJournal.collectAsState()
    val preparation by preparationViewModel.selectedPreparation.collectAsState()
    val planting by plantingViewModel.selectedPlanting.collectAsState()
    val treatment by treatmentViewModel.selectedTreatment.collectAsState()

    var expanded by rememberSaveable { mutableStateOf(false) }
    var analysisText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val isIndonesianLanguage = true // nanti bisa dari settings

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
                    navController.navigate("form_edit/$type/$categoryId")
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = stringResource(id = R.string.plant_journal),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        /* ================= CONTENT ================= */

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            journal?.let {
                JournalCardReadOnly(item = it)
            }

            Spacer(modifier = Modifier.height(12.dp))

            val details = when (type) {
                "preparation" -> stringResource(id = R.string.preparation_detail)
                "planting" -> stringResource(id = R.string.planting_detail)
                "treatment" -> stringResource(id = R.string.treatment_detail)
                else -> ""
            }

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

            Spacer(modifier = Modifier.height(16.dp))

            /* ================= GENERATE BUTTON ================= */

            Button(
                onClick = {
                    scope.launch {
                        val preparationText =
                            preparationViewModel
                                .getSelectedPreparationAsString(isIndonesianLanguage)

                        val plantingText =
                            plantingViewModel
                                .getSelectedPlantingAsString(isIndonesianLanguage)

                        val treatmentText =
                            treatmentViewModel
                                .getSelectedTreatmentAsString(isIndonesianLanguage)

                        val formInput = buildString {
                            preparationText?.let {
                                appendLine("=== DATA PERSIAPAN ===")
                                appendLine(it)
                                appendLine()
                            }
                            plantingText?.let {
                                appendLine("=== DATA PENANAMAN ===")
                                appendLine(it)
                                appendLine()
                            }
                            treatmentText?.let {
                                appendLine("=== DATA PERAWATAN ===")
                                appendLine(it)
                            }
                        }

                        if (formInput.isNotBlank()) {
                            isLoading = true
                            analysisText = analysisViewModel.sendAnalysis(
                                formInput,
                                isIndonesianLanguage
                            )
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                Text(
                    if (isLoading)
                        stringResource(id = R.string.loading)
                    else
                        stringResource(id = R.string.generate_analysis)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.lens_result),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* ================= RESULT ================= */

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text(
                        text = analysisText,
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ================= SAVE ================= */

            Button(
                onClick = {
                    when (type) {
                        "preparation" ->
                            preparationViewModel.updateAnalysisAI(analysisText)
                        "planting" ->
                            plantingViewModel.updateAnalysisAI(analysisText)
                        "treatment" ->
                            treatmentViewModel.updateAnalysisAI(analysisText)
                    }

                    navController.navigate("journal") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = analysisText.isNotBlank()
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}

/* ================= EXPANDABLE CARD ================= */

@Composable
fun ExpandableCard(
    title: String,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .animateContentSize()
            .clickable { onClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontWeight = FontWeight.Medium)

            Icon(
                imageVector = if (expanded)
                    Icons.Default.KeyboardArrowUp
                else
                    Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        Divider(color = Color.Black.copy(alpha = 0.15f))

        if (expanded) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}
