package com.example.plantmate.ui.plantjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
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
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.local.entity.FormEntity.TreatmentEntity
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PlantingLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PreparationLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.TreatmentLocalViewModel
import com.example.plantmate.ui.components.JournalCardReadOnly
import com.example.plantmate.ui.plantjournal.form.PlantingForm
import com.example.plantmate.ui.plantjournal.form.PreparationForm
import com.example.plantmate.ui.plantjournal.form.TreatmentForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantJournalScreen(
    categoryId: Int = 0,
    type: String? = null,
    navController: NavHostController
) {
    val context = LocalContext.current
    val app = context.applicationContext as YourApp

    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val preparationViewModel: PreparationLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val plantingViewModel: PlantingLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val treatmentViewModel: TreatmentLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val journals by journalViewModel.allJournals.collectAsState()
    val journal by journalViewModel.selectedJournal.collectAsState()

    val preparation by preparationViewModel.selectedPreparation.collectAsState()
    val planting by plantingViewModel.selectedPlanting.collectAsState()
    val treatment by treatmentViewModel.selectedTreatment.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }

    var preparationEntity by remember { mutableStateOf<PreparationEntity?>(null) }
    var plantingEntity by remember { mutableStateOf<PlantingEntity?>(null) }
    var treatmentEntity by remember { mutableStateOf<TreatmentEntity?>(null) }

    var selectedJournalId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var showAddJournalDialog by remember { mutableStateOf(false) }

    val isEditMode = categoryId != 0

    /* ================= SET TAB ================= */
    LaunchedEffect(type) {
        if (isEditMode) {
            selectedTab = 0
        }
    }

    /* ================= LOAD EDIT DATA ================= */
    LaunchedEffect(categoryId, type) {
        if (isEditMode) {
            when (type) {
                "preparation" -> preparationViewModel.loadById(categoryId)
                "planting" -> plantingViewModel.loadById(categoryId)
                "treatment" -> treatmentViewModel.loadById(categoryId)
            }
        }
    }

    /* ================= LOAD JOURNAL FROM FORM ================= */
    LaunchedEffect(preparation, planting, treatment) {
        val journalId =
            preparation?.journalId
                ?: planting?.journalId
                ?: treatment?.journalId

        journalId?.let {
            journalViewModel.loadJournalById(it)
            selectedJournalId = it
        }
    }

    /* ================= ADD JOURNAL DIALOG ================= */
    AddJournalDialog(
        show = showAddJournalDialog,
        onDismiss = { showAddJournalDialog = false }
    )

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
                    if (isEditMode) {
                        when (type) {
                            "preparation" -> preparation?.let { preparationViewModel.deletePreparation(it) }
                            "planting" -> planting?.let { plantingViewModel.deletePlanting(it) }
                            "treatment" -> treatment?.let { treatmentViewModel.deleteTreatment(it) }
                        }

                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    } else {
                        navController.popBackStack()
                    }
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

        /* ================= DROPDOWN JOURNAL ================= */
        if (!isEditMode) {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = journals.firstOrNull { it.id == selectedJournalId }?.plantName ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(id = R.string.select_journal)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        journals.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item.plantName) },
                                onClick = {
                                    selectedJournalId = item.id
                                    journalViewModel.loadJournalById(item.id)
                                    expanded = false
                                }
                            )
                        }

                        Divider()

                        DropdownMenuItem(
                            text = { Text("➕ ${stringResource(id = R.string.create_journal)}") },
                            onClick = {
                                expanded = false
                                showAddJournalDialog = true
                            }
                        )
                    }
                }
            }
        }

        /* ================= JOURNAL CARD ================= */
        if (journal != null && (isEditMode || selectedJournalId != null)) {
            Column(modifier = Modifier.padding(16.dp)) {
                JournalCardReadOnly(item = journal!!)
            }
        }

        /* ================= TAB ================= */
        if (journal != null && (isEditMode || selectedJournalId != null)) {

            val tabs = if (isEditMode) {
                listOf(
                    when (type) {
                        "preparation" -> R.string.preparation
                        "planting" -> R.string.planting
                        else -> R.string.treatment
                    }
                )
            } else {
                listOf(
                    R.string.preparation,
                    R.string.planting,
                    R.string.treatment
                )
            }

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White
            ) {
                tabs.forEachIndexed { index, titleRes ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            if (!isEditMode) selectedTab = index
                        },
                        enabled = !isEditMode,
                        text = {
                            Text(text = stringResource(id = titleRes))
                        }
                    )
                }
            }
        }

        /* ================= FORM ================= */
        if (journal != null && (isEditMode || selectedJournalId != null)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                when {
                    isEditMode && type == "preparation" -> {
                        PreparationForm(
                            categoryId = categoryId,
                            journalId = journal!!.id,
                            onFormChange = { preparationEntity = it }
                        )
                    }

                    isEditMode && type == "planting" -> {
                        PlantingForm(
                            categoryId = categoryId,
                            journalId = journal!!.id,
                            onFormChange = { plantingEntity = it }
                        )
                    }

                    isEditMode && type == "treatment" -> {
                        TreatmentForm(
                            categoryId = categoryId,
                            journalId = journal!!.id,
                            onFormChange = { treatmentEntity = it }
                        )
                    }

                    selectedTab == 0 -> PreparationForm(
                        categoryId = categoryId,
                        journalId = journal!!.id,
                        onFormChange = { preparationEntity = it }
                    )

                    selectedTab == 1 -> PlantingForm(
                        categoryId = categoryId,
                        journalId = journal!!.id,
                        onFormChange = { plantingEntity = it }
                    )

                    else -> TreatmentForm(
                        categoryId = categoryId,
                        journalId = journal!!.id,
                        onFormChange = { treatmentEntity = it }
                    )
                }
            }
        }

        /* ================= SAVE BUTTON ================= */
        if (journal != null && (isEditMode || selectedJournalId != null)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = when {
                    isEditMode && type == "preparation" -> preparationEntity != null
                    isEditMode && type == "planting" -> plantingEntity != null
                    isEditMode && type == "treatment" -> treatmentEntity != null
                    selectedTab == 0 -> preparationEntity != null
                    selectedTab == 1 -> plantingEntity != null
                    else -> treatmentEntity != null
                },
                onClick = {
                    when {
                        isEditMode && type == "preparation" ->
                            preparationEntity?.let {
                                preparationViewModel.updatePreparation(it) { id ->
                                    navController.navigate("form_result/preparation/$id")
                                }
                            }

                        isEditMode && type == "planting" ->
                            plantingEntity?.let {
                                plantingViewModel.updatePlanting(it) { id ->
                                    navController.navigate("form_result/planting/$id")
                                }
                            }

                        isEditMode && type == "treatment" ->
                            treatmentEntity?.let {
                                treatmentViewModel.updateTreatment(it) { id ->
                                    navController.navigate("form_result/treatment/$id")
                                }
                            }

                        selectedTab == 0 ->
                            preparationEntity?.let {
                                preparationViewModel.addPreparation(it) { id ->
                                    navController.navigate("form_result/preparation/$id")
                                }
                            }

                        selectedTab == 1 ->
                            plantingEntity?.let {
                                plantingViewModel.addPlanting(it) { id ->
                                    navController.navigate("form_result/planting/$id")
                                }
                            }

                        else ->
                            treatmentEntity?.let {
                                treatmentViewModel.addTreatment(it) { id ->
                                    navController.navigate("form_result/treatment/$id")
                                }
                            }
                    }
                }
            ) {
                Text(stringResource(id = R.string.add))
            }
        }
    }
}
