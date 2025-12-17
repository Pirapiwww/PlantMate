package com.example.plantmate.ui.bookmark

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.plantmate.data.viewmodel.local.EncyclopediaLocalViewModel
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import com.example.plantmate.ui.components.BottomNavBar
import com.example.plantmate.ui.components.EncyclopediaCard
import com.example.plantmate.ui.components.LensCard
import com.example.plantmate.ui.components.PaginationBar
import java.io.File

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    initialTab: Int = 0
) {
    val app = LocalContext.current.applicationContext as YourApp

    // ======================
    // VIEW MODELS
    // ======================
    val encyclopediaViewModel: EncyclopediaLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val lensViewModel: LensLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val encyclopediaList by encyclopediaViewModel.encyclopedia.collectAsState()
    val lensList by lensViewModel.lens.collectAsState()

    // ======================
    // TAB STATE
    // ======================
    var selectedTab by remember { mutableStateOf(initialTab) }

    val tabs = listOf(
        stringResource(R.string.plant_lens),
        stringResource(R.string.plant_encyclopedia)
    )

    // ======================
    // PAGINATION
    // ======================
    val itemsPerPage = 4
    var currentPage by remember { mutableStateOf(1) }

    LaunchedEffect(selectedTab) {
        currentPage = 1
    }

    val currentList =
        if (selectedTab == 0) lensList else encyclopediaList

    val totalPages =
        (currentList.size + itemsPerPage - 1) / itemsPerPage

    if (currentPage > totalPages && totalPages > 0) {
        currentPage = 1
    }

    val pagedItems =
        currentList
            .drop((currentPage - 1) * itemsPerPage)
            .take(itemsPerPage)

    // ======================
    // UI
    // ======================
    val navbarItems = DataSource().loadNavbar()
    val topColor = Color(0xFFDDE6C7)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ======================
            // TOP BAR
            // ======================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(topColor)
                    .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = stringResource(id = R.string.navbar_bookmark),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(24.dp))
            }

            // ======================
            // TAB ROW
            // ======================
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
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

            // ======================
            // CONTENT
            // ======================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp, bottom = 80.dp)
            ) {

                when (selectedTab) {

                    // ======================
                    // PLANT LENS TAB
                    // ======================
                    0 -> {
                        if (lensList.isEmpty()) {
                            Text(
                                text = stringResource(R.string.empty_encyclopedia),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        } else {

                            pagedItems.forEach { item ->
                                val lens = item as com.example.plantmate.data.local.entity.LensEntity

                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    LensCard(
                                        item = lens,
                                        onClick = { id ->
                                            navController.navigate("lens/Bookmark/$id")
                                        },
                                        onDelete = {
                                            lens.lensImage?.let {
                                                try {
                                                    val file = File(Uri.parse(it).path ?: "")
                                                    if (file.exists()) {
                                                        file.delete()
                                                    }
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }
                                            }

                                            lensViewModel.deleteLens(lens)
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
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

                    // ======================
                    // PLANT ENCYCLOPEDIA TAB
                    // ======================
                    1 -> {
                        if (encyclopediaList.isEmpty()) {
                            Text(
                                text = stringResource(R.string.empty_encyclopedia),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        } else {

                            pagedItems.forEach { item ->
                                val plant =
                                    item as com.example.plantmate.data.local.entity.EncyclopediaEntity

                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    EncyclopediaCard(
                                        item = plant,
                                        onClick = { id ->
                                            navController.navigate("localCareGuide/Bookmark/$id")
                                        },
                                        onDelete = {
                                            encyclopediaViewModel.deleteEncyclopedia(plant)
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
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
        }

        // ======================
        // BOTTOM NAVBAR
        // ======================
        BottomNavBar(
            navbarItems = navbarItems,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}