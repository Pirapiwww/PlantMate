package com.example.plantmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plantmate.R
import com.example.plantmate.data.local.entity.JournalEntity

// ======================================
// Versi full: onClick + onDelete
// ======================================
@Composable
fun JournalCard(
    item: JournalEntity,
    onClick: (Int) -> Unit,
    onDelete: (JournalEntity) -> Unit,

) {
    val context = LocalContext.current

    val randomIndex = remember {
        (1..12).random()
    }

    val imageName = "journal_$randomIndex"

    val drawableResId = remember {
        context.resources.getIdentifier(
            imageName,
            "drawable",
            context.packageName
        ).takeIf { it != 0 } ?: R.drawable.journal_1
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick(item.id) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = drawableResId,
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${stringResource(id = R.string.journal_format)}${item.id} ${item.plantName}",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${stringResource(id = R.string.created)} ${item.createdDate}",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "Delete",
            tint = Color.Red,
            modifier = Modifier
                .size(28.dp)
                .clickable { onDelete(item) }
        )
    }
}

// ======================================
// Versi tanpa onDelete
// ======================================
@Composable
fun JournalCardNoDelete(
    item: JournalEntity,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // 🎲 Random gambar (1–9)
    val randomIndex = remember {
        (1..12).random()
    }

    val imageName = "journal_$randomIndex"

    val drawableResId = remember {
        context.resources.getIdentifier(
            imageName,
            "drawable",
            context.packageName
        ).takeIf { it != 0 } ?: R.drawable.journal_1
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick(item.id) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = drawableResId,
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${stringResource(id = R.string.journal_format)}${item.id} ${item.plantName}",
                fontWeight = FontWeight.Bold,
                maxLines = 1
                )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${stringResource(id = R.string.created)} ${item.createdDate}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}


// ======================================
// Versi read-only
// ======================================
@Composable
fun JournalCardReadOnly(
    item: JournalEntity
) {
    val context = LocalContext.current

    val randomNumber = (1..9).random()
    val image = "journal_$randomNumber"

    // Cari drawable resource dari nama di entity (journal_1 sampai journal_12)
    val drawableResId = remember(image) {
        val name = image ?: ""
        context.resources.getIdentifier(
            name,
            "drawable",
            context.packageName
        ).takeIf { it != 0 } ?: R.drawable.journal_1
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        AsyncImage(
            model = drawableResId,
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${stringResource(id = R.string.journal_format)}${item.id} ${item.plantName}",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${stringResource(id = R.string.created)} ${item.createdDate}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}