package com.example.plantmate.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.plantmate.data.DataSource
import com.example.plantmate.ui.components.BottomNavBar

@Composable
fun BookmarkScreen(navController: NavHostController) {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Plant Lens", "Plant Encyclopedia")

    // Bottom Navbar
    var selectedNav by remember { mutableStateOf(2) }
    val navbarItems = DataSource().loadNavbar()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ============================
            //           TOP BAR
            // ============================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDDE6C7))
                    .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Bookmark",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(24.dp))
            }

            // ============================
            //           TAB MENU
            // ============================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEachIndexed { index, title ->

                    Column(
                        modifier = Modifier
                            .clickable { selectedTab = index }
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (selectedTab == index) Color.Black else Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        if (selectedTab == index) {
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(
                                        if (index == 0) 70.dp else 120.dp
                                    )
                                    .background(Color(0xFF3A4D39))
                            )
                        } else {
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
            }

            // ============================
            //        CONTENT (3 CARD)
            // ============================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                repeat(3) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color(0xFFE5E5E5), RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // ============================
        //       BOTTOM NAVBAR
        // ============================
        BottomNavBar(
            navbarItems = navbarItems,
            selectedIndex = selectedNav,
            onItemSelected = { selectedNav = it },
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

