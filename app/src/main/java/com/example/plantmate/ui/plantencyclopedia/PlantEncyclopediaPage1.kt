package com.example.plantmate.ui.plantencyclopedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.example.plantmate.R
import com.example.plantmate.data.DataSource
import com.example.plantmate.model.PlantEncyclopedia

@Composable
fun PlantEncyclopediaSearchScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Back Button
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Title centered
            Text(
                stringResource(id = R.string.plant_news),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp)) // supaya teks benar-benar center
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section Title
        Text(
            text = "Search Result",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // News List
        EncyclopediaList(
            encyclopediaList = DataSource().loadEncyclopedia(),
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun EncyclopediaCard(encyclopedia: PlantEncyclopedia, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Thumbnail
        Image(
            painter = painterResource(encyclopedia.image),
            contentDescription = null,
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Texts
        Column(modifier = Modifier.fillMaxWidth()) {

            // Title
            Text(
                text = stringResource(encyclopedia.title),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Harvest
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Minimal Umur Panen")
                Text(text = stringResource(encyclopedia.harvest))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Difficult
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Penanaman")

                Box(
                    modifier = Modifier
                        .background(Color.Yellow, RoundedCornerShape(20.dp))
                        .size(width = 60.dp, height = 20.dp)
                ) {
                    Text(
                        text = stringResource(encyclopedia.difficult),
                    )
                }
            }

            // Bookmark Button
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Yellow, RoundedCornerShape(30.dp))
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text("Bookmark", fontWeight = FontWeight.Medium)
                }
            }

        }
    }
}


@Composable
fun EncyclopediaList(encyclopediaList: List<PlantEncyclopedia>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(encyclopediaList) { encyclopedia ->
            EncyclopediaCard(
                encyclopedia = encyclopedia,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEncyclopediaScreen() {
    PlantEncyclopediaSearchScreen()
}
