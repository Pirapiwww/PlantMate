package com.example.plantmate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.DataSource
import com.example.plantmate.data.viewmodel.local.EncyclopediaLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import com.example.plantmate.data.viewmodel.local.NewsLocalViewModel
import com.example.plantmate.ui.components.*

@Composable
fun PlantHomeScreen(navController: NavHostController) {

    val app = LocalContext.current.applicationContext as YourApp

    val newsViewModel: NewsLocalViewModel = viewModel(factory = app.viewModelFactory)
    val encyclopediaViewModel: EncyclopediaLocalViewModel = viewModel(factory = app.viewModelFactory)
    val lensViewModel: LensLocalViewModel = viewModel(factory = app.viewModelFactory)
    val journalViewModel: JournalLocalViewModel = viewModel(factory = app.viewModelFactory)

    val newsList by newsViewModel.news.collectAsState()
    val isLoading by newsViewModel.isLoading.collectAsState()

    val encyclopediaRow by encyclopediaViewModel.encyclopedia.collectAsState()
    val lensRow by lensViewModel.lens.collectAsState()
    val journalRow by journalViewModel.allJournals.collectAsState()

    val featureNavItems = DataSource.loadFeature()
    val navbarItems = DataSource.loadNavbar()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp)
        ) {

            // ================= BANNER =================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_img),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ================= FEATURE MENU =================
            FeatureList(
                featureList = featureNavItems,
                onFeatureClick = { feature ->
                    when (feature.route) {
                        "plantJournal" -> navController.navigate("plantJournal")
                        "plantLens" -> navController.navigate("plantLens")
                        "plantEncyclopedia" -> navController.navigate("plantEncyclopedia")
                        "plantNews" -> navController.navigate("plantNews")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ================= JOURNAL (1 item) =================
            journalRow.firstOrNull()?.let { item ->
                HomeSectionTitle(
                    text = stringResource(R.string.my_journal),
                    onSeeDetail = {
                        navController.navigate("journal") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )

                SwipeNavigateCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onNavigate = {
                        navController.navigate("journal") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }                    }
                ) {
                    JournalCardNoDelete(
                        item = item,
                        onClick = { id -> navController.navigate("myjournal/home/$id") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ================= ENCYCLOPEDIA (1 item) =================
            encyclopediaRow.firstOrNull()?.let { item ->
                HomeSectionTitle(
                    text = stringResource(R.string.encyclopedia_bookmark),
                    onSeeDetail = {
                        navController.navigate("bookmark?tab=1") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )

                SwipeNavigateCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onNavigate = {
                        navController.navigate("bookmark?tab=1") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                ) {
                    EncyclopediaCardSimple(
                        item = item,
                        onClick = { id -> navController.navigate("localCareGuide/Home/$id") }
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            // ================= LENS (1 item) =================
            lensRow.firstOrNull()?.let { item ->
                HomeSectionTitle(
                    text = stringResource(R.string.lens_bookmark),
                    onSeeDetail = {
                        navController.navigate("bookmark?tab=0") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
                SwipeNavigateCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onNavigate = {
                        navController.navigate("bookmark?tab=0") {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                ) {
                    LensCardSimple(
                        item = item,
                        onClick = { id -> navController.navigate("lens/Home/$id") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ================= NEWS (1 item) =================
            if (newsList.isNotEmpty()) {
                HomeSectionTitle(
                    text = stringResource(R.string.latest_news),
                    onSeeDetail = {
                        navController.navigate("plantNews")
                    }
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    SwipeNavigateCard(
                        modifier = Modifier,
                        onNavigate = {
                            navController.navigate("plantNews")
                        }
                    ) {
                        NewsCard(
                            news = newsList.first(),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }

        // ================= BOTTOM NAV =================
        BottomNavBar(
            navbarItems = navbarItems,
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun HomeSectionTitle(
    text: String,
    onSeeDetail: () -> Unit = {}
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, style = MaterialTheme.typography.titleSmall)
        Text(
            text = stringResource(id = R.string.detail) + " >>",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.clickable { onSeeDetail() }
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun SwipeNavigateCard(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var hasNavigated by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    // CARD KE KIRI (offset negatif)
                    offsetX = (offsetX + delta).coerceIn(-300f, 0f)
                },
                onDragStopped = {
                    if (offsetX < -160 && !hasNavigated) {
                        hasNavigated = true
                        onNavigate()
                    }
                    offsetX = 0f
                }
            )
    ) {

        // ===== DETAIL TETAP DI KANAN =====
        if (offsetX < -40) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow_circle),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.detail),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        // ===== CARD GESER KE KIRI =====
        Box(
            modifier = Modifier.offset { IntOffset(offsetX.toInt(), 0) }
        ) {
            content()
        }
    }
}
