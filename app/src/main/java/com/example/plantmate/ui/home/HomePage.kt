package com.example.plantmate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.plantmate.R
import com.example.plantmate.data.DataSource

import com.example.plantmate.ui.components.FeatureList
import com.example.plantmate.ui.components.BottomNavBar

@Composable
fun PlantHomeScreen(navController: NavHostController) {

    var search by remember { mutableStateOf("") }
    val featureNavItems = DataSource().loadFeature()

    // bottom navbar
    var selectedNav by remember { mutableStateOf(0) }
    val navbarItems = DataSource().loadNavbar()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ===================== CONTENT SCROLL =====================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)   // beri space agar tidak ketutup bottom bar
        ) {

            // ---------------------- BANNER ----------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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

                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it },
                        placeholder = { Text("Search") },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .background(Color.White, RoundedCornerShape(50)),
                        shape = RoundedCornerShape(50),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = Color.Black
                        )
                    )

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

            // ---------------------- MENU 4 FITUR ----------------------
            FeatureList(
                featureList = featureNavItems,      // List<FeatureIcon>
                onFeatureClick = { feature ->
                    when (feature.route) {       // route dari model FeatureIcon
                        "plantJournal" -> navController.navigate("plantJournal")
                        "plantLens"      -> navController.navigate("plantLens")
                        "plantEncyclopedia"      -> navController.navigate("plantEncyclopedia")
                        "plantNews"      -> navController.navigate("plantNews")
                        else            -> {}
                    }
                }
            )

            // ---------------------- YOUR JOURNAL ----------------------
            HomeSectionTitle("Your Journal")
            HomeCardPlaceholder()

            // ---------------------- YOUR BOOKMARK ----------------------
            HomeSectionTitle("Your Bookmark")
            HomeCardPlaceholder()

            // ---------------------- LATEST PLANT NEWS ----------------------
            HomeSectionTitle("Latest Plant News")
        }

        // ===================== BOTTOM NAVIGATION =====================
        BottomNavBar(
            navbarItems = navbarItems,
            selectedIndex = selectedNav,
            onItemSelected = { selectedNav = it },
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun HomeSectionTitle(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, style = MaterialTheme.typography.titleSmall)
        Text("See Detail >>", style = MaterialTheme.typography.labelSmall)
    }
    Spacer(modifier = Modifier.height(6.dp))
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
    Spacer(modifier = Modifier.height(14.dp))
}


