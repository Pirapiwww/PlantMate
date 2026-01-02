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
import com.example.plantmate.data.DataSource
import com.example.plantmate.data.DataSource.toStringRes
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity

@Composable
fun PlantingContent(data: PlantingEntity) {

    // ===================== MAP KEY → ENUM =====================

    val method = DataSource.PlantingMethod.values()
        .firstOrNull { it.key == data.method }

    val location = DataSource.LocationType.values()
        .firstOrNull { it.key == data.location }

    val frequency = DataSource.FrequencyType.values()
        .firstOrNull { it.key == data.frequency }

    val amount = DataSource.AmountType.values()
        .firstOrNull { it.key == data.amount }

    // ===================== UI =====================

    LabelValue(
        stringResource(R.string.title),
        data.title
    )

    LabelValue(
        stringResource(R.string.method),
        method?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    LabelValue(
        stringResource(R.string.location),
        location?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    LabelValue(
        stringResource(R.string.frequency),
        frequency?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    LabelValue(
        stringResource(R.string.amount),
        amount?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    Spacer(modifier = Modifier.height(12.dp))
    Divider(color = Color.Black.copy(alpha = 0.15f))
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "📝 ${stringResource(R.string.note)}",
        color = MaterialTheme.colorScheme.primary
    )

    Text(data.note ?: "-")
}
