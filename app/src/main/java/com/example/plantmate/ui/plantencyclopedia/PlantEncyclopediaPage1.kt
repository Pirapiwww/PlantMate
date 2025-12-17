package com.example.plantmate.ui.plantencyclopedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.repository.EncyclopediaRepository
import com.example.plantmate.data.viewmodel.EncyclopediaViewModel
import com.example.plantmate.data.viewmodel.EncyclopediaViewModelFactory
import com.example.plantmate.data.viewmodel.local.EncyclopediaLocalViewModel
import com.example.plantmate.data.viewmodel.local.SearchLocalViewModel
import com.example.plantmate.ui.components.PlantResultCard
import com.example.plantmate.ui.components.PaginationBar
import com.example.plantmate.ui.components.SearchHistoryCard

@Composable
fun PlantEncyclopediaSearchScreen(
    repo: EncyclopediaRepository,
    onBack: () -> Unit,
    onDetailClick: (Int) -> Unit = {}
) {
    val viewModel: EncyclopediaViewModel = viewModel(
        factory = EncyclopediaViewModelFactory(repo)
    )

    val app = LocalContext.current.applicationContext as YourApp

    // search (history)
    val searchViewModel: SearchLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val historyList by searchViewModel.search.collectAsState()


    val queryState = remember { mutableStateOf("") }
    val results by viewModel.results.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // pagination
    val pageSize = 4
    var currentPage by remember { mutableStateOf(1) }

    // search
    val isSearching = queryState.value.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                text = stringResource(id = R.string.plant_encyclopedia),
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
                currentPage = 1
                viewModel.setQuery(it)
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (queryState.value.isNotBlank()) {
                        searchViewModel.addSearch(queryState.value)
                    }
                }
            ),
            placeholder = {Text(text = stringResource(id = R.string.search)) },
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
            text = if (queryState.value.isBlank())
                stringResource(id = R.string.search_history)
            else
                stringResource(id = R.string.search_result),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // =========================
        //      RESULT STATE
        // =========================

        val sortedResults = remember(results) {
            results.sortedByDescending {
                !it.default_image?.thumbnail.isNullOrEmpty() ||
                        !it.default_image?.medium_url.isNullOrEmpty()
            }
        }

        val totalPages = remember(sortedResults) {
            (sortedResults.size + pageSize - 1) / pageSize
        }

        val pagedResults = remember(sortedResults, currentPage) {
            sortedResults
                .drop((currentPage - 1) * pageSize)
                .take(pageSize)
        }

        // =========================
        //      RESULT / HISTORY
        // =========================

        if (queryState.value.isBlank()) {

            // ---------- SEARCH HISTORY ----------
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(historyList) { item ->
                    SearchHistoryCard(
                        item = item,
                        onClick = { history ->
                            queryState.value = history
                            currentPage = 1
                            viewModel.setQuery(history)
                        },
                        onDelete = {
                            searchViewModel.deleteSearch(it)
                        }
                    )
                }
            }

        } else {

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                !error.isNullOrEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = error ?: "",
                            color = Color.Red
                        )
                    }
                }

                pagedResults.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No Result",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                else -> {

                    // ===== LIST RESULT =====
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        pagedResults.forEach { plant ->
                            PlantResultCard(
                                item = plant,
                                onClick = {
                                    val query = queryState.value.trim()

                                    if (query.isNotBlank()) {
                                        val isExist = historyList.any {
                                            it.history.equals(query, ignoreCase = true)
                                        }

                                        if (!isExist) {
                                            searchViewModel.addSearch(query)
                                        }
                                    }

                                    onDetailClick(plant.id)
                                }
                            )
                        }
                    }

                    // ===== PAGINATION =====
                    if (totalPages > 1) {
                        PaginationBar(
                            currentPage = currentPage,
                            totalPages = totalPages,
                            onPageChange = { currentPage = it }
                        )
                    }
                }
            }
        }
    }
}

