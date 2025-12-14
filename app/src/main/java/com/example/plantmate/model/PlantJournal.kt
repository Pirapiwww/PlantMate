package com.example.plantmate.model

import androidx.annotation.DrawableRes

data class Journal (
    @DrawableRes val image: Int,
    val journalName: String,
    val date: String
)

data class JournalPlanting (
    val phase: String,
    val plantName: String,
    val method: String,
    val frequency: String,
    val amount: String,
    val note: String
)

data class JournalPreparation (
    val phase: String,
    val plantName: String,
    val plantType: String,
    val source: String,
    val soilType: String,
    val fertilizerType: String,
    val note: String
)

data class JournalTreatment (
    val phase: String,
    val plantName: String,
    val plantCondition: String,
    val treatmentType: String,
    val problem: String,
    val solution: String,
    val note: String
)