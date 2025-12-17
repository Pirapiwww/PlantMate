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
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.viewmodel.local.FormVM.PlantingLocalViewModel
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

    LaunchedEffect(categoryId) {
        if (categoryId != 0) {
            plantingViewModel.loadById(categoryId)
        }
    }

    val planting by plantingViewModel.selectedPlanting.collectAsState()

    val ds = DataSource()

    /* ===================== STATE ===================== */
    var title by rememberSaveable { mutableStateOf("") }
    var method by rememberSaveable { mutableStateOf<Int?>(null) }
    var location by rememberSaveable { mutableStateOf<Int?>(null) }
    var frequency by rememberSaveable { mutableStateOf<Int?>(null) }
    var amount by rememberSaveable { mutableStateOf<Int?>(null) }
    var notes by rememberSaveable { mutableStateOf("") }

    /* ===================== PREFILL ===================== */
    LaunchedEffect(planting) {
        planting?.let {
            title = it.title
            method = ds.loadMethod().find { res ->
                context.getString(res) == it.method
            }
            location = ds.loadLocation().find { res ->
                context.getString(res) == it.location
            }
            frequency = ds.loadFrequency().find { res ->
                context.getString(res) == it.frequency
            }
            amount = ds.loadAmount().find { res ->
                context.getString(res) == it.amount
            }
            notes = it.note.orEmpty()
        }
    }

    val isFormValid =
        title.isNotBlank() &&
                method != null &&
                location != null &&
                frequency != null &&
                amount != null

    val createdDate = remember {
        SimpleDateFormat(
            "dd MMM yyyy",
            Locale("id", "ID")
        ).format(Date())
    }

    /* ===================== SEND ENTITY ===================== */
    LaunchedEffect(
        title,
        method,
        location,
        frequency,
        amount,
        notes
    ) {
        if (isFormValid) {
            onFormChange(
                PlantingEntity(
                    id = planting?.id ?: 0,
                    title = title,
                    journalId = journalId,
                    method = context.getString(method!!),
                    location = context.getString(location!!),
                    frequency = context.getString(frequency!!),
                    amount = context.getString(amount!!),
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

    Text(stringResource(id = R.string.title), Modifier.padding(top = 16.dp, bottom = 6.dp))
    OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        placeholder = {
            Text(text = stringResource(id = R.string.title))
        },
        modifier = Modifier.fillMaxWidth()
    )

    Text(stringResource(R.string.method), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = method,
        placeholder = R.string.method,
        items = ds.loadMethod(),
        onSelected = { method = it }
    )

    Text(stringResource(R.string.location), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = location,
        placeholder = R.string.location,
        items = ds.loadLocation(),
        onSelected = { location = it }
    )

    Text(stringResource(R.string.frequency), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = frequency,
        placeholder = R.string.frequency,
        items = ds.loadFrequency(),
        onSelected = { frequency = it }
    )

    Text(stringResource(R.string.amount), Modifier.padding(top = 16.dp, bottom = 6.dp))
    JournalDropdown(
        value = amount,
        placeholder = R.string.amount,
        items = ds.loadAmount(),
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
