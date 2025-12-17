package com.example.plantmate.ui.plantnews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.plantmate.data.viewmodel.local.NewsLocalViewModel
import com.example.plantmate.ui.components.NewsCard
import com.example.plantmate.ui.components.PaginationBar

@Composable
fun PlantNewsScreen(
    onBack: () -> Unit
) {

    val app = LocalContext.current.applicationContext as YourApp

    val viewModel: NewsLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val newsList by viewModel.news.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // ======================
    // SEARCH
    // ======================
    var query by remember { mutableStateOf("") }

    val filteredNews = remember(newsList, query) {
        if (query.isBlank()) {
            newsList
        } else {
            newsList.filter {
                it.newsTitle.contains(query, ignoreCase = true)
            }
        }
    }

    // ======================
    // PAGINATION
    // ======================
    val itemsPerPage = 4
    var currentPage by remember { mutableStateOf(1) }
    val totalPages = (filteredNews.size + itemsPerPage - 1) / itemsPerPage

    if (currentPage > totalPages && totalPages > 0) {
        currentPage = 1
    }

    val pagedItems = filteredNews
        .drop((currentPage - 1) * itemsPerPage)
        .take(itemsPerPage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ======================
        // TOP BAR
        // ======================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 12.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = stringResource(id = R.string.plant_news),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ======================
        // SEARCH BAR
        // ======================
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                currentPage = 1
            },
            placeholder = { Text(stringResource(id = R.string.search_news)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ======================
        // TITLE
        // ======================
        Text(
            text = if (query.isBlank()) stringResource(id = R.string.latest_news) else stringResource(id = R.string.search_result),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ======================
        // UI STATE
        // ======================
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

            filteredNews.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_news),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    pagedItems.forEach { item ->
                        NewsCard(news = item)
                    }
                }

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
