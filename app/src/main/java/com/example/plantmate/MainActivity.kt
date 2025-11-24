package com.example.plantmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantmate.ui.theme.PlantMateTheme

import com.example.plantmate.ui.plantjournal.PlantJournalScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantMateTheme {
                PlantJournalScreen()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlantJournalScreenPreview() {
    PlantMateTheme {
        PlantJournalScreen()
    }
}

