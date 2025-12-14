package com.example.plantmate.ui.careguide

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.example.plantmate.YourApp
import com.example.plantmate.data.viewmodel.local.EncyclopediaLocalViewModel
import com.example.plantmate.data.viewmodel.local.ViewModelFactory
import com.example.plantmate.model.CareGuideItem

@Composable
fun CareGuideScreen(
    careGuide: CareGuideItem,
    imageUrl: String?,
    onBack: () -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current

    val viewModel: EncyclopediaLocalViewModel = viewModel(
        factory = ViewModelFactory(
            (context.applicationContext as YourApp).encyclopediaLocalRepository,
            (context.applicationContext as YourApp).newsLocalRepository,
            (context.applicationContext as YourApp).lensLocalRepository
            )
    )

    val isBookmarked by viewModel
        .isBookmarked(careGuide.common_name)
        .collectAsState(initial = false)


    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ----------------------
        // TOP BAR
        // ----------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                stringResource(id = R.string.plant_encyclopedia),
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

            // ----------------------
            // IMAGE
            // ----------------------
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = careGuide.common_name,
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
                        color = Color.DarkGray
                    )
                }
            }

            // ----------------------
            // TITLE
            // ----------------------
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = careGuide.common_name ?: "-",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                careGuide.scientific_name?.let {
                    Text(
                        text = it.joinToString(", "),
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // ----------------------
            // SECTIONS
            // ----------------------
            careGuide.section?.forEachIndexed { index, section ->
                val title = when (section.type?.lowercase()) {
                    "watering" -> stringResource(R.string.watering)
                    "sunlight" -> stringResource(R.string.sunlight)
                    "pruning" -> stringResource(R.string.pruning)
                    else -> "Unknown Section"
                }

                ExpandableItem(
                    title = title,
                    content = section.description ?: "-",
                    expanded = expandedIndex == index,
                    onClick = {
                        expandedIndex = if (expandedIndex == index) null else index
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ----------------------
            // BOOKMARK BUTTON (ROOT JUMP)
            // ----------------------
            Button(
                onClick = {
                    if (isBookmarked) return@Button   // extra safety

                    var wateringDesc: String? = null
                    var sunlightDesc: String? = null
                    var pruningDesc: String? = null

                    careGuide.section?.forEach { section ->
                        when (section.type?.lowercase()) {
                            "watering" -> wateringDesc = section.description
                            "sunlight" -> sunlightDesc = section.description
                            "pruning" -> pruningDesc = section.description
                        }
                    }

                    viewModel.addEncyclopedia(
                        commonName = careGuide.common_name,
                        scientificName = careGuide.scientific_name?.joinToString(", "),
                        sunlightDesc = sunlightDesc,
                        wateringDesc = wateringDesc,
                        pruningDesc = pruningDesc,
                        imageUrl = imageUrl
                    )

                    navController.navigate("bookmark?tab=1") {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = !isBookmarked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE0E0E0),
                    disabledContentColor = Color.DarkGray
                )
            ) {
                Text(
                    text = if (isBookmarked) "Bookmarked" else "Bookmark"
                )
            }
        }
    }
}

// ----------------------------------------------------------------------
// Component Expandable Item
// ----------------------------------------------------------------------
@Composable
fun ExpandableItem(
    title: String,
    content: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { onClick() }
            .padding(horizontal = 0.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontWeight = FontWeight.Medium)
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        Divider(color = Color.Black.copy(alpha = 0.2f))

        if (expanded) {
            Text(
                text = content,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = Color.DarkGray
            )
        }

        Divider(color = Color.Black.copy(alpha = 0.2f))
    }
}
