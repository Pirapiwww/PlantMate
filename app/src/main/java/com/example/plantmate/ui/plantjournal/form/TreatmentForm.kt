package com.example.plantmate.ui.plantjournal.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.plantmate.R
import com.example.plantmate.data.DataSource
import com.example.plantmate.ui.components.dropdowns.JournalDropdown

@Composable
fun TreatmentForm(
    plantName: String,
    plantCondition: String,
    treatmentType: String,
    problem: String,
    solution: String,
    notes: String,
    onValueChange: (String, String) -> Unit
) {

    val ds = DataSource()

    // PLANT NAME
    Text(
        stringResource(R.string.plant_name),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    OutlinedTextField(
        value = plantName,
        onValueChange = { onValueChange("plantName", it) },
        label = { Text(stringResource(R.string.plant_name)) },
        modifier = Modifier.fillMaxWidth()
    )

    // CONDITION
    Text(
        stringResource(R.string.plant_condition),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.plant_condition)),
        items = ds.loadCondition(),
        onSelected = { onValueChange("plantCondition", it) }
    )

    // TREATMENT TYPE
    Text(
        stringResource(R.string.treatment_type),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    JournalDropdown(
        placeholder = stringResource(R.string.select_x, stringResource(R.string.treatment_type)),
        items = ds.loadTreatment(),
        onSelected = { onValueChange("treatmentType", it) }
    )

    // PROBLEM
    Text(
        stringResource(R.string.problem),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    OutlinedTextField(
        value = problem,
        onValueChange = { onValueChange("problem", it) },
        label = { Text(stringResource(R.string.problem)) },
        modifier = Modifier.fillMaxWidth(),
        minLines = 4
    )

    // SOLUTION
    Text(
        stringResource(R.string.solution),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    OutlinedTextField(
        value = solution,
        onValueChange = { onValueChange("solution", it) },
        label = { Text(stringResource(R.string.solution)) },
        modifier = Modifier.fillMaxWidth(),
        minLines = 4
    )

    // NOTES
    Text(
        stringResource(R.string.note),
        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
    )
    OutlinedTextField(
        value = notes,
        onValueChange = { onValueChange("notes", it) },
        label = { Text(stringResource(R.string.note)) },
        modifier = Modifier.fillMaxWidth(),
        minLines = 6
    )
}
