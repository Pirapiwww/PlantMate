package com.example.plantmate.ui.plantjournal.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.plantmate.R
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity


@Composable
fun PlantingContent(data: PlantingEntity) {
    LabelValue(stringResource(id = R.string.title), data.title)
    LabelValue(stringResource(id = R.string.method), data.method)
    LabelValue(stringResource(id = R.string.location), data.location)
    LabelValue(stringResource(id = R.string.frequency), data.frequency)
    LabelValue(stringResource(id = R.string.amount), data.amount)

    Spacer(modifier = Modifier.height(12.dp))
    Divider(color = Color.Black.copy(alpha = 0.15f))
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "📝 ${stringResource(id = R.string.note)}",
        color = MaterialTheme.colorScheme.primary
    )
    Text(data.note ?: "-")
}
