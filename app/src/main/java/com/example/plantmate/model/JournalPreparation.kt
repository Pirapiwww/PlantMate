package com.example.plantmate.model

import androidx.annotation.StringRes

data class JournalPreparation (
    @StringRes val phase: Int,
    @StringRes val date: Int,
    @StringRes val plantName: Int,
    @StringRes val plantType: Int,
    @StringRes val media: Int,
    @StringRes val note: Int
)