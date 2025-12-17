package com.example.plantmate.ui.myjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.DataSource
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.ui.components.BottomNavBar
import com.example.plantmate.ui.components.JournalCard
import com.example.plantmate.ui.components.PaginationBar

@Composable
fun MyJournalScreen(
    navController: NavHostController
) {

    val app = LocalContext.current.applicationContext as YourApp

    // ======================
    // VIEW MODEL
    // ======================
    val journalViewModel: JournalLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val journalList by journalViewModel.allJournals.collectAsState()

    // ======================
    // PAGINATION
    // ======================
    val itemsPerPage = 4
    var currentPage by remember { mutableStateOf(1) }

    val totalPages =
        (journalList.size + itemsPerPage - 1) / itemsPerPage

    if (currentPage > totalPages && totalPages > 0) {
        currentPage = 1
    }

    val pagedJournals =
        journalList
            .drop((currentPage - 1) * itemsPerPage)
            .take(itemsPerPage)

    val navbarItems = DataSource().loadNavbar()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // ============================
            //           TOP BAR
            // ============================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDDE6C7))
                    .padding(
                        top = 38.dp,
                        bottom = 16.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = stringResource(id = R.string.my_journal),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(24.dp))
            }

            // ============================
            //           CONTENT
            // ============================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                if (journalList.isEmpty()) {

                    Text(
                        text = stringResource(id = R.string.empty_journal),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )

                } else {

                    pagedJournals.forEach { journal ->

                        JournalCard(
                            item = journal,
                            onClick = { journalId ->
                                navController.navigate("myjournal/journal/$journalId")
                            },
                            onDelete = {
                                journalViewModel.deleteJournal(it)
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))
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

        // ============================
        //       BOTTOM NAVBAR
        // ============================
        BottomNavBar(
            navbarItems = navbarItems,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
