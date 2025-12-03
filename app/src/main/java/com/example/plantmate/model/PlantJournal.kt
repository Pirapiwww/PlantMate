package com.example.plantmate.model

import androidx.annotation.StringRes

data class JournalPlanting (
    @StringRes val phase: Int,
    @StringRes val seeds: Int,
    @StringRes val date: Int,
    @StringRes val how: Int,
    @StringRes val conditions: Int,
    @StringRes val note: Int
)

data class JournalPreparation (
    @StringRes val phase: Int,
    @StringRes val date: Int,
    @StringRes val plantName: Int,
    @StringRes val plantType: Int,
    @StringRes val media: Int,
    @StringRes val note: Int
)

data class JournalTreatment (
    @StringRes val phase: Int,
    @StringRes val date: Int,
    @StringRes val plantCondition: Int,
    @StringRes val watering: Int,
    @StringRes val fertilizer: Int,
    @StringRes val problem: Int,
    @StringRes val solution: Int,
    @StringRes val note: Int

)