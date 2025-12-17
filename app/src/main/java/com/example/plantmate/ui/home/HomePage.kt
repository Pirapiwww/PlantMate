package com.example.plantmate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.DataSource
import com.example.plantmate.data.viewmodel.local.EncyclopediaLocalViewModel
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import com.example.plantmate.data.viewmodel.local.NewsLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.ui.components.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

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

    val featureNavItems = DataSource().loadFeature()
    val navbarItems = DataSource().loadNavbar()

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

            // ---------------------- BANNER ----------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home_img),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, start = 16.dp, end = 16.dp)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .background(Color.White, RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_plantmate_text),
                            contentDescription = "PlantMate Logo",
                            modifier = Modifier.height(20.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("P", color = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ============== 4 MENU FITUR ==============
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

            // ========================== JOURNAL ==========================
            if (journalRow.isNotEmpty()) {
                HomeSectionTitle(
                    text = stringResource(R.string.my_journal),
                    onSeeDetail = {
                        navController.navigate("journal") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )

                val pagerState = rememberPagerState(pageCount = { journalRow.size })
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                    val item = journalRow[page]
                    JournalCardNoDelete(
                        item = item,
                        onClick = { id -> navController.navigate("myjournal/home/$id") },
                        modifier = Modifier
                            .width(340.dp)
                            .padding(start = 16.dp)
                    )
                }
            }

            // ========================== ENCYCLOPEDIA ==========================
            if (encyclopediaRow.isNotEmpty()) {
                HomeSectionTitle(
                    text = stringResource(R.string.encyclopedia_bookmark),
                    onSeeDetail = {
                        navController.navigate("bookmark?tab=1") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )

                val pagerState = rememberPagerState(pageCount = { encyclopediaRow.size })
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                    val item = encyclopediaRow[page]
                    EncyclopediaCardSimple(
                        item = item,
                        onClick = { id -> navController.navigate("localCareGuide/Home/$id") },
                        modifier = Modifier
                            .width(340.dp)
                            .padding(start = 16.dp)
                    )
                }
            }

            // ========================== LENS ==========================
            if (lensRow.isNotEmpty()) {
                HomeSectionTitle(
                    text = stringResource(R.string.lens_bookmark),
                    onSeeDetail = {
                        navController.navigate("bookmark?tab=0") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )

                val pagerState = rememberPagerState(pageCount = { lensRow.size })
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                    val item = lensRow[page]
                    LensCardSimple(
                        item = item,
                        onClick = { id -> navController.navigate("lens/Home/$id") },
                        modifier = Modifier
                            .width(340.dp)
                            .padding(start = 16.dp)
                    )
                }
            }

            // ========================== NEWS ==========================
            HomeSectionTitle(
                text = stringResource(R.string.latest_news),
                onSeeDetail = { navController.navigate("plantNews") }
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
            } else if (newsList.isNotEmpty()) {
                val pagerState = rememberPagerState(pageCount = { newsList.size })
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                    val item = newsList[page]
                    NewsCard(
                        news = item,
                        modifier = Modifier.width(370.dp)
                    )
                }
            }

        }

        // ========================== BOTTOM NAV ==========================
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
            text = stringResource(id = R.string.detail),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.clickable { onSeeDetail() }
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}
