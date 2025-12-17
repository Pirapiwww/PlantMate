package com.example.plantmate.ui.myjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.viewmodel.local.FormVM.PlantingLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PreparationLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.TreatmentLocalViewModel
import com.example.plantmate.ui.components.PaginationBar
import com.example.plantmate.ui.components.PlantingCard
import com.example.plantmate.ui.components.PreparationCard
import com.example.plantmate.ui.components.TreatmentCard

@Composable
fun JournalCategoryScreen(
    journalId: Int,
    routeBack: String,
    navController: NavHostController
) {
    val app = LocalContext.current.applicationContext as YourApp

    val preparationVM: PreparationLocalViewModel =
        viewModel(factory = app.viewModelFactory)
    val plantingVM: PlantingLocalViewModel =
        viewModel(factory = app.viewModelFactory)
    val treatmentVM: TreatmentLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    LaunchedEffect(journalId) {
        preparationVM.loadByJournalId(journalId)
        plantingVM.loadByJournalId(journalId)
        treatmentVM.loadByJournalId(journalId)
    }

    val preparations by preparationVM.preparationsByJournal.collectAsState()
    val plantings by plantingVM.plantingsByJournal.collectAsState()
    val treatments by treatmentVM.treatmentsByJournal.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    var currentPage by remember { mutableStateOf(1) }

    val itemsPerPage = 4

    LaunchedEffect(selectedTab) {
        currentPage = 1
    }

    val tabs = listOf(
        stringResource(R.string.preparation),
        stringResource(R.string.planting),
        stringResource(R.string.treatment)
    )

    val currentListSize = when (selectedTab) {
        0 -> preparations.size
        1 -> plantings.size
        else -> treatments.size
    }

    val totalPages =
        (currentListSize + itemsPerPage - 1) / itemsPerPage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {

            /* ================= TOP BAR ================= */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDDE6C7))
                    .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (routeBack == "journal"){
                            navController.navigate("journal") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        } else if (routeBack == "home"){
                            navController.navigate("home") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }                    }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Text(
                    text = stringResource(id = R.string.my_journal),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.width(24.dp))
            }

            /* ================= TAB ROW ================= */
            TabRow(
                selectedTabIndex = selectedTab,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFF6FA66E),
                        height = 3.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            /* ================= CONTENT ================= */
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp, bottom = 80.dp)
            ) {

                when (selectedTab) {

                    /* ============ PREPARATION ============ */
                    0 -> {
                        if (preparations.isEmpty()) {
                            EmptyText(stringResource(id = R.string.empty_preparation))
                        } else {
                            paginate(preparations, currentPage, itemsPerPage).forEach {
                                Column(modifier = Modifier.padding(horizontal = 16.dp)){
                                    PreparationCard(
                                        item = it,
                                        onClick = { id ->
                                            navController.navigate("journal/preparation/$id")
                                        },
                                        onDelete = {
                                            preparationVM.deletePreparation(it)
                                        }
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                            }
                        }
                    }

                    /* ============ PLANTING ============ */
                    1 -> {
                        if (plantings.isEmpty()) {
                            EmptyText(stringResource(id = R.string.empty_planting))
                        } else {
                            paginate(plantings, currentPage, itemsPerPage).forEach {
                                Column(modifier = Modifier.padding(horizontal = 16.dp)){
                                    PlantingCard(
                                        item = it,
                                        onClick = { id ->
                                            navController.navigate("journal/planting/$id")
                                        },
                                        onDelete = {
                                            plantingVM.deletePlanting(it)
                                        }
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                            }
                        }
                    }

                    /* ============ TREATMENT ============ */
                    2 -> {
                        if (treatments.isEmpty()) {
                            EmptyText(stringResource(id = R.string.empty_treatment))
                        } else {
                            paginate(treatments, currentPage, itemsPerPage).forEach {
                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    TreatmentCard(
                                        item = it,
                                        onClick = { id ->
                                            navController.navigate("journal/treatment/$id")
                                        },
                                        onDelete = {
                                            treatmentVM.deleteTreatment(it)
                                        }
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                            }
                        }
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

@Composable
private fun EmptyText(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium
    )
}

fun <T> paginate(
    list: List<T>,
    currentPage: Int,
    itemsPerPage: Int
): List<T> =
    list.drop((currentPage - 1) * itemsPerPage).take(itemsPerPage)

