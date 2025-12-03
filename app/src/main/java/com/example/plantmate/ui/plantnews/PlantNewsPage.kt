package com.example.plantmate.ui.plantnews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantmate.R
import com.example.plantmate.data.viewmodel.NewsViewModel
import com.example.plantmate.ui.components.NewsCard

@Composable
fun PlantNewsScreen(
    viewModel: NewsViewModel = viewModel(),
    onBack: () -> Unit
) {
    val newsList = viewModel.newsList
    val isLoading = viewModel.isLoading

    // Pagination states
    val itemsPerPage = 4
    var currentPage by remember { mutableStateOf(1) }
    val totalPages = (newsList.size + itemsPerPage - 1) / itemsPerPage

    // Slice item yang mau ditampilkan
    val pagedItems = newsList
        .drop((currentPage - 1) * itemsPerPage)
        .take(itemsPerPage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Top Bar (unchanged)
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
                text = stringResource(id = R.string.plant_news),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Latest Plant News",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Loading State
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                items(pagedItems) { news ->
                    NewsCard(news = news)
                }
            }

            // =====================
            //    PAGINATION BAR
            // =====================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                val windowSize = 3 // hanya 3 nomor

                // Hitung start–end window
                val half = windowSize / 2
                var start = (currentPage - half).coerceAtLeast(1)
                val end = (start + windowSize - 1).coerceAtMost(totalPages)
                start = (end - windowSize + 1).coerceAtLeast(1)

                // ==================
                // BAGIAN KIRI
                // ==================
                if (start > 1) {
                    TextButton(onClick = { currentPage = 1 }) {
                        Text("1")
                    }
                    Text(
                        text = "…",
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                    )
                }

                // ==================
                // WINDOW (3 nomor)
                // ==================
                for (i in start..end) {
                    TextButton(onClick = { currentPage = i }) {
                        Text(
                            text = i.toString(),
                            color = if (i == currentPage)
                                MaterialTheme.colorScheme.primary
                            else Color.Black
                        )
                    }
                }

                // ==================
                // BAGIAN KANAN
                // ==================
                if (end < totalPages) {
                    Text(
                        text = "…",
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                    )
                    TextButton(onClick = { currentPage = totalPages }) {
                        Text(
                            text = totalPages.toString(),
                            color = Color.Black // end tidak pakai warna khusus
                        )
                    }
                }
            }

        }
    }
}
