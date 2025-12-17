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
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PreparationLocalViewModel
import com.example.plantmate.ui.components.dropdowns.JournalDropdown
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PreparationForm(
    categoryId: Int,
    journalId: Int,
    onFormChange: (PreparationEntity?) -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as YourApp

    val preparationViewModel: PreparationLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    /* ================= LOAD DATA ================= */

    LaunchedEffect(categoryId) {
        if (categoryId != 0) {
            preparationViewModel.loadById(categoryId)
        }
    }

    LaunchedEffect(journalId) {
        journalViewModel.loadJournalById(journalId)
    }

    val preparation by preparationViewModel.selectedPreparation.collectAsState()
    val journal by journalViewModel.selectedJournal.collectAsState()

    val ds = DataSource()

    /* ================= STATE ================= */

    var title by rememberSaveable { mutableStateOf("") }
    var plantType by rememberSaveable { mutableStateOf<Int?>(null) }
    var source by rememberSaveable { mutableStateOf<Int?>(null) }
    var soilType by rememberSaveable { mutableStateOf<Int?>(null) }
    var fertilizerType by rememberSaveable { mutableStateOf<Int?>(null) }
    var notes by rememberSaveable { mutableStateOf("") }

    /* ================= PREFILL ================= */

    LaunchedEffect(preparation) {
        preparation?.let {
            title = it.title
            plantType = ds.loadPlantType().find { res ->
                context.getString(res) == it.plantType
            }
            source = ds.loadSource().find { res ->
                context.getString(res) == it.source
            }
            soilType = ds.loadSoilType().find { res ->
                context.getString(res) == it.soilType
            }
            fertilizerType = ds.loadFertilizerType().find { res ->
                context.getString(res) == it.fertilizerType
            }
            notes = it.note.orEmpty()
        }
    }

    val isFormValid =
        plantType != null &&
                source != null &&
                soilType != null &&
                fertilizerType != null

    val createdDate = remember {
        SimpleDateFormat(
            "dd MMM yyyy",
            Locale("id", "ID")
        ).format(Date())
    }

    /* ================= SEND ENTITY ================= */

    LaunchedEffect(
        title,
        plantType,
        source,
        soilType,
        fertilizerType,
        notes
    ) {
        if (isFormValid) {
            onFormChange(
                PreparationEntity(
                    id = preparation?.id ?: 0,
                    journalId = journalId,
                    title = title,
                    plantType = context.getString(plantType!!),
                    source = context.getString(source!!),
                    soilType = context.getString(soilType!!),
                    fertilizerType = context.getString(fertilizerType!!),
                    note = notes.ifBlank { null },
                    analysisAI = preparation?.analysisAI ?: "",
                    createdDate = preparation?.createdDate ?: createdDate
                )
            )
        } else {
            onFormChange(null)
        }
    }

    /* ================= UI ================= */

    Text(stringResource(id = R.string.title), Modifier.padding(top = 16.dp, bottom = 6.dp))
    OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        placeholder = {
            Text(text = stringResource(id = R.string.title))
        },
        modifier = Modifier.fillMaxWidth()
    )


    Text(stringResource(R.string.plant_type), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = plantType,
        placeholder = R.string.plant_type,
        items = ds.loadPlantType(),
        onSelected = { plantType = it }
    )

    Text(stringResource(R.string.source), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = source,
        placeholder = R.string.source,
        items = ds.loadSource(),
        onSelected = { source = it }
    )

    Text(stringResource(R.string.soil_type), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = soilType,
        placeholder = R.string.soil_type,
        items = ds.loadSoilType(),
        onSelected = { soilType = it }
    )

    Text(stringResource(R.string.fertilizer_type), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = fertilizerType,
        placeholder = R.string.fertilizer_type,
        items = ds.loadFertilizerType(),
        onSelected = { fertilizerType = it }
    )

    Text(stringResource(R.string.note), Modifier.padding(top = 16.dp, bottom = 6.dp))
    OutlinedTextField(
        value = notes,
        onValueChange = { notes = it },
        modifier = Modifier.fillMaxWidth(),
        minLines = 5
    )
}
