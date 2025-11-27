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
import com.example.plantmate.R
import com.example.plantmate.data.DataSource

import com.example.plantmate.ui.components.FeatureList

@Composable
fun PlantHomeScreen() {

    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // ---------------------- BANNER DENGAN SEARCH DI ATASNYA ----------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            // Banner Image
            Image(
                painter = painterResource(id = R.drawable.img_news1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Search & Profile overlay di atas gambar
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
                        .background(Color.White, RoundedCornerShape(50)),  // background putih solid
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,

                        focusedBorderColor = Color.Transparent,   // hilangkan border
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.Black
                    )
                )


                Spacer(modifier = Modifier.width(12.dp))

                // Profile Button (tanpa background)
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
            featureList = DataSource().loadFeature(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------------------- YOUR JOURNAL ----------------------
        HomeSectionTitle("Your Journal")

        HomeCardPlaceholder()

        // ---------------------- YOUR BOOKMARK ----------------------
        HomeSectionTitle("Your Bookmark")

        HomeCardPlaceholder()

        // ---------------------- LATEST PLANT NEWS ----------------------
        HomeSectionTitle("Latest Plant News")

        HomeCardPlaceholder()
    }
}

@Composable
fun HomeMenuItem(title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFEAEAEA))
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPlantHomeScreen() {
    PlantHomeScreen()
}
