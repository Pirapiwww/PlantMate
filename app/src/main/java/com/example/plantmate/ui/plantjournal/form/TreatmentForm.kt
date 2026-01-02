package com.example.plantmate.ui.plantjournal.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.DataSource
import com.example.plantmate.data.DataSource.toStringRes
import com.example.plantmate.data.local.entity.FormEntity.TreatmentEntity
import com.example.plantmate.data.viewmodel.local.FormVM.TreatmentLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.isIndonesianLanguage
import com.example.plantmate.ui.components.dropdowns.JournalDropdown
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TreatmentForm(
    categoryId: Int,
    journalId: Int,
    onFormChange: (TreatmentEntity?) -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as YourApp

    val treatmentViewModel: TreatmentLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    /* ================= LOAD DATA ================= */

    LaunchedEffect(categoryId) {
        if (categoryId != 0) {
            treatmentViewModel.loadById(categoryId)
        }
    }

    LaunchedEffect(journalId) {
        journalViewModel.loadJournalById(journalId)
    }

    val treatment by treatmentViewModel.selectedTreatment.collectAsState()
    val journal by journalViewModel.selectedJournal.collectAsState()

    /* ================= STATE ================= */

    val title = remember(journal, isIndonesianLanguage()) {
        journal?.plantName?.let { plantName ->
            if (isIndonesianLanguage()) "Perawatan $plantName"
            else "Treatment $plantName"
        } ?: ""
    }

    var plantCondition by rememberSaveable {
        mutableStateOf<DataSource.PlantCondition?>(null)
    }
    var treatmentType by rememberSaveable {
        mutableStateOf<DataSource.TreatmentType?>(null)
    }
    var problem by rememberSaveable { mutableStateOf("") }
    var solution by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }

    /* ================= PREFILL ================= */

    LaunchedEffect(treatment) {
        treatment?.let {
            plantCondition = DataSource.PlantCondition.values()
                .firstOrNull { e -> e.key == it.plantCondition }

            treatmentType = DataSource.TreatmentType.values()
                .firstOrNull { e -> e.key == it.treatmentType }

            problem = it.problem
            solution = it.solution
            notes = it.note.orEmpty()
        }
    }

    val isFormValid =
        plantCondition != null &&
                treatmentType != null &&
                problem.isNotBlank() &&
                solution.isNotBlank()

    val createdDate = remember {
        SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
            .format(Date())
    }

    /* ================= SEND ENTITY ================= */

    LaunchedEffect(
        plantCondition,
        treatmentType,
        problem,
        solution,
        notes,
        title
    ) {
        if (isFormValid) {
            onFormChange(
                TreatmentEntity(
                    id = treatment?.id ?: 0,
                    journalId = journalId,
                    title = title,

                    // ⬇️ SIMPAN KEY ENUM (AMAN MULTI BAHASA)
                    plantCondition = plantCondition!!.key,
                    treatmentType = treatmentType!!.key,

                    problem = problem,
                    solution = solution,
                    note = notes.ifBlank { null },
                    analysisAI = treatment?.analysisAI ?: "",
                    createdDate = treatment?.createdDate ?: createdDate
                )
            )
        } else {
            onFormChange(null)
        }
    }

    /* ================= UI (TIDAK DIUBAH) ================= */

    Text(
        text = stringResource(id = R.string.title),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = title,
        onValueChange = {},
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.title)) }
    )

    Text(stringResource(R.string.plant_condition), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = plantCondition,
        placeholder = R.string.plant_condition,
        items = DataSource.loadCondition(),
        labelRes = { it.toStringRes() },
        onSelected = { plantCondition = it }
    )

    Text(stringResource(R.string.treatment_type), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = treatmentType,
        placeholder = R.string.treatment_type,
        items = DataSource.loadTreatment(),
        labelRes = { it.toStringRes() },
        onSelected = { treatmentType = it }
    )

    Text(stringResource(R.string.problem), Modifier.padding(top = 16.dp, bottom = 6.dp))
    OutlinedTextField(
        value = problem,
        onValueChange = { problem = it },
        modifier = Modifier.fillMaxWidth(),
        minLines = 4
    )

    Text(stringResource(R.string.solution), Modifier.padding(top = 16.dp, bottom = 6.dp))
    OutlinedTextField(
        value = solution,
        onValueChange = { solution = it },
        modifier = Modifier.fillMaxWidth(),
        minLines = 4
    )

    Text(stringResource(R.string.note), Modifier.padding(top = 16.dp, bottom = 6.dp))
    OutlinedTextField(
        value = notes,
        onValueChange = { notes = it },
        modifier = Modifier.fillMaxWidth(),
        minLines = 5
    )
}
