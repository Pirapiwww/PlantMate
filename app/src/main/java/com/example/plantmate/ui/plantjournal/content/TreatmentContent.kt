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
import com.example.plantmate.data.local.entity.FormEntity.TreatmentEntity

@Composable
fun TreatmentContent(data: TreatmentEntity) {

    // ===================== MAP KEY → ENUM =====================

    val plantCondition = DataSource.PlantCondition.values()
        .firstOrNull { it.key == data.plantCondition }

    val treatmentType = DataSource.TreatmentType.values()
        .firstOrNull { it.key == data.treatmentType }

    // ===================== UI =====================

    LabelValue(
        stringResource(R.string.title),
        data.title
    )

    LabelValue(
        stringResource(R.string.plant_condition),
        plantCondition?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    LabelValue(
        stringResource(R.string.treatment_type),
        treatmentType?.let { stringResource(it.toStringRes()) } ?: "-"
    )

    Spacer(modifier = Modifier.height(8.dp))

    LongText(
        stringResource(R.string.problem),
        data.problem
    )

    Spacer(modifier = Modifier.height(8.dp))

    LongText(
        stringResource(R.string.solution),
        data.solution
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
