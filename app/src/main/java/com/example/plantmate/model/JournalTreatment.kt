package com.example.plantmate.model

import androidx.annotation.StringRes

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