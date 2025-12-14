package com.example.plantmate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.plantmate.ui.components.FeatureList
import com.example.plantmate.ui.components.BottomNavBar
import com.example.plantmate.ui.components.EncyclopediaCardSimple
import com.example.plantmate.ui.components.LensCardSimple
import com.example.plantmate.ui.components.NewsCard
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState


@Composable
fun PlantHomeScreen(navController: NavHostController) {

    // Ambil dari room
    val app = LocalContext.current.applicationContext as YourApp

    val newsViewModel: NewsLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val encyclopediaViewModel: EncyclopediaLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val lensViewModel: LensLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    // untuk news
    val newsList by newsViewModel.news.collectAsState()
    val latestNews = newsList

    val isLoading by newsViewModel.isLoading.collectAsState()

    // untuk encyclopedia
    val encyclopediaList by encyclopediaViewModel.encyclopedia.collectAsState()
    val encylopediaRow = encyclopediaList

    // untuk lens
    val lensList by lensViewModel.lens.collectAsState()
    val lensRow = lensList

    var search by remember { mutableStateOf("") }
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
                            modifier = Modifier
                                .height(20.dp), // sama persis kayak form
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

            // ==========================
            // SECTIONS LAIN
            // ==========================
            HomeSectionTitle("My Journal")
            HomeCardPlaceholder()

            // encyclopedia
            HomeSectionTitle(
                text = stringResource(id = R.string.encyclopedia_bookmark),
                onSeeDetail = {
                    navController.navigate("bookmark?tab=1") {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
            if (encylopediaRow.isEmpty()) {
                Text(
                    text = "No saved encyclopedia yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 24.dp)
                )
            } else {
                val pagerState = rememberPagerState(
                    pageCount = { encylopediaRow.size }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->

                    val item = encylopediaRow[page]

                    EncyclopediaCardSimple(
                        item = item,
                        onClick = { id ->
                            navController.navigate("localCareGuide/Home/$id")
                        },
                        modifier = Modifier
                            .width(340.dp)
                            .padding(start = 16.dp)
                    )
                }
            }

            // lens
            HomeSectionTitle(
                text = stringResource(id = R.string.lens_bookmark),
                onSeeDetail = { }
            )
            if (lensRow.isEmpty()) {
                Text(
                    text = "No saved Plant Lens yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 24.dp)
                )
            } else {
                val pagerState = rememberPagerState(
                    pageCount = { lensRow.size }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->

                    val item = lensRow[page]

                    LensCardSimple(
                        item = item,
                        onClick = { id ->
                            navController.navigate("lens/Home/$id")
                        },
                        modifier = Modifier
                            .width(340.dp)
                            .padding(start = 16.dp)
                    )
                }
            }

            // news
            HomeSectionTitle(
                text = stringResource(id = R.string.latest_news),
                onSeeDetail = { navController.navigate("plantNews") }
            )
            if (latestNews.isEmpty() && isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                val pagerState = rememberPagerState(
                    pageCount = { latestNews.size }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->

                    val item = latestNews[page]

                    NewsCard(
                        news = item,
                        modifier = Modifier.width(370.dp)
                    )
                }
            }
        }

        // ============== BOTTOM NAV ==============
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
            "See Detail >>",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.clickable { onSeeDetail() }
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun HomeCardPlaceholder() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE6E6E6))
    )
    Spacer(modifier = Modifier.height(14.dp)) }
