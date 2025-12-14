package com.example.plantmate.ui.bookmark

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.plantmate.ui.components.BottomNavBar
import com.example.plantmate.ui.components.EncyclopediaCard
import com.example.plantmate.data.viewmodel.local.EncyclopediaLocalViewModel
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import com.example.plantmate.ui.components.LensCard
import java.io.File

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    initialTab: Int = 0
) {
    val app = LocalContext.current.applicationContext as YourApp

    // encyclopedia
    val encyclopediaViewModel: EncyclopediaLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val encyclopediaList by encyclopediaViewModel.encyclopedia.collectAsState()

    // lens
    val lensViewModel: LensLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val lensList by lensViewModel.lens.collectAsState()

    var selectedTab by remember { mutableStateOf(initialTab) }
    val tabs = listOf(
        stringResource(R.string.plant_lens),
        stringResource(R.string.plant_encyclopedia)
    )

    // Bottom Navbar
    val navbarItems = DataSource().loadNavbar()
    val topColor = Color(0xFFDDE6C7)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()) {

            // ---------------- TOP BAR ----------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(topColor)
                    .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = stringResource(id = R.string.bookmark),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(24.dp))
            }

            // ---------------- TAB ROW ----------------
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color.Black,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFF6FA66E),   // hijau saat aktif
                        height = 3.dp
                    )
                },
                divider = {
                    // garis dividers bawah tab row dibuat putih agar tidak terlihat
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(Color.White)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->

                    val isSelected = selectedTab == index

                    Tab(
                        selected = isSelected,
                        onClick = { selectedTab = index },
                        selectedContentColor = Color(0xFF6FA66E),  // teks hijau saat aktif
                        unselectedContentColor = Color.Black,      // teks hitam saat non aktif
                        text = { Text(title) }
                    )
                }
            }

            // ---------------- CONTENT ----------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp, top = 16.dp)

            ) {

                when (selectedTab) {

                    // ===== PLANT LENS TAB =====
                    0 -> {
                        if (lensList.isEmpty()) {
                            Text(
                                text = stringResource(R.string.empty_encyclopedia),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp)
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        } else {
                            lensList.forEach { lens ->
                                Column(modifier = Modifier.padding(horizontal = 16.dp)){
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

                                            lensViewModel.deleteLens(it)
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                        }
                    }

                    // ===== PLANT ENCYCLOPEDIA TAB =====
                    1 -> {

                        if (encyclopediaList.isEmpty()) {
                            Text(
                                text = stringResource(R.string.empty_encyclopedia),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp)
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        } else {
                            encyclopediaList.forEach { plant ->
                                Column(modifier = Modifier.padding(horizontal = 16.dp)){
                                    EncyclopediaCard(
                                        item = plant,
                                        onClick = { id ->
                                            navController.navigate("localCareGuide/Bookmark/$id")
                                        },
                                        onDelete = {
                                            encyclopediaViewModel.deleteEncyclopedia(it)
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }

        // ---------------- BOTTOM NAVBAR ----------------
        BottomNavBar(
            navbarItems = navbarItems,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}
