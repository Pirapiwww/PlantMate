package com.example.plantmate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PaginationBar(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit
) {
    val windowSize = 3

    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val half = windowSize / 2
        var start = (currentPage - half).coerceAtLeast(1)
        val end = (start + windowSize - 1).coerceAtMost(totalPages)
        start = (end - windowSize + 1).coerceAtLeast(1)

        if (start > 1) {
            TextButton(onClick = { onPageChange(1) }) {
                Text(
                    "1",
                    color = Color.Companion.Black
                )
            }
            Text(
                "…",
                modifier = Modifier.Companion
                    .padding(top = 8.dp)
            )
        }

        for (i in start..end) {
            TextButton(onClick = { onPageChange(i) }) {
                Text(
                    text = i.toString(),
                    color = if (i == currentPage)
                        MaterialTheme.colorScheme.primary
                    else Color.Companion.Black
                )
            }
        }

        if (end < totalPages) {
            Text(
                "…",
                modifier = Modifier.Companion
                    .padding(top = 8.dp)
            )
            TextButton(onClick = { onPageChange(totalPages) }) {
                Text(
                    totalPages.toString(),
                    color = Color.Companion.Black
                )
            }
        }
    }
}