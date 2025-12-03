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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plantmate.model.CareGuideItem

@Composable
fun CareGuideScreen(
    careGuide: CareGuideItem,
    imageUrl: String?,          // <--- gambar dari PlantListItem
    onBack: () -> Unit
) {
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                text = careGuide.common_name ?: "Care Guide",
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
            // GAMBAR DARI PLANT LIST
            // ----------------------
            imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = careGuide.common_name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ----------------------
            // JUDUL + SCIENTIFIC NAME
            // ----------------------
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = careGuide.common_name ?: "-",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                careGuide.scientific_name?.let { sci ->
                    Text(
                        text = sci.joinToString(", "),
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // ----------------------
            // EXPANDABLE CARE GUIDE
            // ----------------------
            careGuide.section?.forEachIndexed { index, section ->
                val title = section.type?.replaceFirstChar { it.uppercase() } ?: "Unknown Section"
                val content = section.description ?: "-"

                ExpandableItem(
                    title = title,
                    content = content,
                    expanded = expandedIndex == index,
                    onClick = { expandedIndex = if (expandedIndex == index) null else index }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// ----------------------------------------------------------------------
// Component untuk Expandable Item
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
