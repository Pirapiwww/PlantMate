package com.example.plantmate.ui.plantencyclopedia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantmate.data.repository.EncyclopediaRepository
import com.example.plantmate.data.viewmodel.EncyclopediaViewModel
import com.example.plantmate.data.viewmodel.EncyclopediaViewModelFactory
import com.example.plantmate.ui.components.PlantResultCard


@Composable
fun PlantEncyclopediaSearchScreen(
    repo: EncyclopediaRepository,
    onBack: () -> Unit,
    onDetailClick: (Int) -> Unit = {}
) {
    val viewModel: EncyclopediaViewModel = viewModel(
        factory = EncyclopediaViewModelFactory(repo)
    )

    val queryState = remember { mutableStateOf("") }
    val results by viewModel.results.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // =========================
        //         TOP BAR
        // =========================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
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
                text = "Plant Encyclopedia",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================
        //        SEARCH BAR
        // =========================
        OutlinedTextField(
            value = queryState.value,
            onValueChange = {
                queryState.value = it
                viewModel.setQuery(it)
            },
            placeholder = { Text("Search plant...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // =========================
        //       SECTION TITLE
        // =========================
        Text(
            text = "Search Result",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // =========================
        //      RESULT STATE
        // =========================
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            !error.isNullOrEmpty() -> {
                Text(
                    text = error ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            results.isEmpty() && queryState.value.isNotEmpty() -> {
                Text(
                    "No Result",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(results) { plant ->
                        PlantResultCard(
                            item = plant,
                            onClick = { onDetailClick(plant.id) }
                        )
                    }
                }
            }
        }
    }
}

