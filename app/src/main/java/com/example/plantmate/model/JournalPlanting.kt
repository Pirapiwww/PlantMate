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