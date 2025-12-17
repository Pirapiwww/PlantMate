package com.example.plantmate.ui.plantjournal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddJournalDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (!show) return

    val context = LocalContext.current
    val app = context.applicationContext as YourApp
    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    var name by remember { mutableStateOf("") }

    // Fungsi saat tombol Add diklik
    val onAdd = {
        if (name.isNotBlank()) {
            val createdDate = SimpleDateFormat(
                "dd MMM yyyy",
                Locale("id", "ID")
            ).format(Date())

            journalViewModel.addJournal(name, createdDate)
            onDismiss()
        } else {
            onDismiss()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.create_journal))
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Input title
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "${stringResource(id = R.string.plant_name)} (${stringResource(id = R.string.title)})" ) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onAdd) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
