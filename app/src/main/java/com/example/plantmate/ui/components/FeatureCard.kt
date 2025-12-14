package com.example.plantmate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.plantmate.model.FeatureIcon

@Composable
fun FeatureList(
    featureList: List<FeatureIcon>,
    modifier: Modifier = Modifier,
    onFeatureClick: (FeatureIcon) -> Unit   // ⬅ callback baru
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        featureList.forEach { feature ->
            FeatureCard(
                feature = feature,
                modifier = Modifier.padding(8.dp),
                onClick = onFeatureClick
            )
        }
    }
}


@Composable
fun FeatureCard(
    feature: FeatureIcon,
    modifier: Modifier = Modifier,
    onClick: (FeatureIcon) -> Unit
) {
    Column(
        modifier = modifier
            .width(75.dp)
            .clickable { onClick(feature) },   // ⬅ klik
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color(0xFFDDE6C7), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(feature.featureImage),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(feature.featureTitle),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            minLines = 2
        )
    }
}
