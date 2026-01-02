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
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PlantingLocalViewModel
import com.example.plantmate.isIndonesianLanguage
import com.example.plantmate.ui.components.dropdowns.JournalDropdown
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantingForm(
    categoryId: Int,
    journalId: Int,
    onFormChange: (PlantingEntity?) -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as YourApp

    val plantingViewModel: PlantingLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    /* ===================== LOAD DATA ===================== */

    LaunchedEffect(categoryId) {
        if (categoryId != 0) {
            plantingViewModel.loadById(categoryId)
        }
    }

    LaunchedEffect(journalId) {
        journalViewModel.loadJournalById(journalId)
    }

    val planting by plantingViewModel.selectedPlanting.collectAsState()
    val journal by journalViewModel.selectedJournal.collectAsState()

    /* ===================== STATE ===================== */

    val title = remember(journal, isIndonesianLanguage()) {
        journal?.plantName?.let { plantName ->
            if (isIndonesianLanguage()) "Penanaman $plantName"
            else "Planting $plantName"
        } ?: ""
    }

    var method by rememberSaveable {
        mutableStateOf<DataSource.PlantingMethod?>(null)
    }
    var location by rememberSaveable {
        mutableStateOf<DataSource.LocationType?>(null)
    }
    var frequency by rememberSaveable {
        mutableStateOf<DataSource.FrequencyType?>(null)
    }
    var amount by rememberSaveable {
        mutableStateOf<DataSource.AmountType?>(null)
    }
    var notes by rememberSaveable { mutableStateOf("") }

    /* ===================== PREFILL ===================== */

    LaunchedEffect(planting) {
        planting?.let {
            method = DataSource.PlantingMethod.values()
                .firstOrNull { e -> e.key == it.method }

            location = DataSource.LocationType.values()
                .firstOrNull { e -> e.key == it.location }

            frequency = DataSource.FrequencyType.values()
                .firstOrNull { e -> e.key == it.frequency }

            amount = DataSource.AmountType.values()
                .firstOrNull { e -> e.key == it.amount }

            notes = it.note.orEmpty()
        }
    }

    val isFormValid =
        method != null &&
                location != null &&
                frequency != null &&
                amount != null

    val createdDate = remember {
        SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
            .format(Date())
    }

    /* ===================== SEND ENTITY ===================== */

    LaunchedEffect(
        method,
        location,
        frequency,
        amount,
        notes,
        title
    ) {
        if (isFormValid) {
            onFormChange(
                PlantingEntity(
                    id = planting?.id ?: 0,
                    title = title,
                    journalId = journalId,
                    method = method!!.key,
                    location = location!!.key,
                    frequency = frequency!!.key,
                    amount = amount!!.key,
                    note = notes.ifBlank { null },
                    analysisAI = planting?.analysisAI ?: "",
                    createdDate = planting?.createdDate ?: createdDate
                )
            )
        } else {
            onFormChange(null)
        }
    }

    /* ===================== UI ===================== */

    Text(
        text = stringResource(R.string.title),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )

    OutlinedTextField(
        value = title,
        onValueChange = {},
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.title)) }
    )

    Text(stringResource(R.string.method), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = method,
        placeholder = R.string.method,
        items = DataSource.loadPlantingMethod(),
        labelRes = { it.toStringRes() },
        onSelected = { method = it }
    )

    Text(stringResource(R.string.location), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = location,
        placeholder = R.string.location,
        items = DataSource.loadLocation(),
        labelRes = { it.toStringRes() },
        onSelected = { location = it }
    )

    Text(stringResource(R.string.frequency), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = frequency,
        placeholder = R.string.frequency,
        items = DataSource.loadFrequency(),
        labelRes = { it.toStringRes() },
        onSelected = { frequency = it }
    )

    Text(stringResource(R.string.amount), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = amount,
        placeholder = R.string.amount,
        items = DataSource.loadAmount(),
        labelRes = { it.toStringRes() },
        onSelected = { amount = it }
    )

    Text(stringResource(R.string.note), Modifier.padding(top = 16.dp, bottom = 6.dp))
    OutlinedTextField(
        value = notes,
        onValueChange = { notes = it },
        modifier = Modifier.fillMaxWidth(),
        minLines = 5
    )
}
