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
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity

@Composable
fun PreparationContent(data: PreparationEntity) {

    // ===================== MAP KEY → ENUM =====================

    val plantType = DataSource.PlantType.values()
        .firstOrNull { it.key == data.plantType }

    val source = DataSource.SourceType.values()
        .firstOrNull { it.key == data.source }

    val soilType = DataSource.SoilType.values()
        .firstOrNull { it.key == data.soilType }

    val fertilizerType = DataSource.FertilizerType.values()
        .firstOrNull { it.key == data.fertilizerType }

    // ===================== UI =====================

    LabelValue(
        stringResource(R.string.title),
        data.title
    )

    LabelValue(
        stringResource(R.string.plant_type),
        plantType?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    LabelValue(
        stringResource(R.string.source),
        source?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    LabelValue(
        stringResource(R.string.soil_type),
        soilType?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    LabelValue(
        stringResource(R.string.fertilizer_type),
        fertilizerType?.let { stringResource(it.toStringRes()) } ?: "-"
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
