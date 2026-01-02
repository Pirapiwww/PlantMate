package com.example.plantmate.ui.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.plantmate.R
import com.example.plantmate.data.local.entity.EncyclopediaEntity
import com.example.plantmate.data.viewmodel.TranslateViewModel
import com.example.plantmate.isIndonesianLanguage
import com.example.plantmate.ui.careguide.ExpandableItem

@Composable
fun BookmarkEncyclopediaScreen(
    encyclopedia: EncyclopediaEntity,
    navController: NavHostController,
    routeBack: String
) {
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    val translateVM: TranslateViewModel = viewModel()
    val translatedSections by translateVM.translatedSections.collectAsState()

    // Kirim teks untuk diterjemahkan saat screen muncul
    LaunchedEffect(encyclopedia) {
        val sectionsText = listOfNotNull(
            encyclopedia.sunlightDesc,
            encyclopedia.wateringDesc,
            encyclopedia.pruningDesc
        )
        translateVM.translateSections(
            sectionsText,
            if (isIndonesianLanguage()) "id" else "en"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        //---------------------
        // TOP BAR
        //---------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (routeBack == "Bookmark") {
                        navController.navigate("bookmark?tab=1") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else if (routeBack == "Home") {
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(id = R.string.navbar_bookmark),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            //---------------------
            // GAMBAR
            //---------------------
            if (!encyclopedia.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = encyclopedia.imageUrl,
                    contentDescription = encyclopedia.commonName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_image),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
            }

            //------------------------
            // NAME + SCIENTIFIC NAME
            //------------------------
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = encyclopedia.commonName ?: "-",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = encyclopedia.scientificName ?: "-",
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            //------------------------
            // EXPANDABLE SECTIONS DENGAN TRANSLATE
            //------------------------
            val sections = listOfNotNull(
                Triple("Sunlight", encyclopedia.sunlightDesc, R.string.sunlight),
                Triple("Watering", encyclopedia.wateringDesc, R.string.watering),
                Triple("Pruning", encyclopedia.pruningDesc, R.string.pruning)
            )

            sections.forEachIndexed { index, (key, value, stringId) ->

                val content = translatedSections.getOrNull(index) ?: value ?: "-"

                ExpandableItem(
                    title = stringResource(id = stringId),
                    content = content,
                    expanded = expandedIndex == index,
                    onClick = { expandedIndex = if (expandedIndex == index) null else index }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
