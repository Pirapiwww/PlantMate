package com.example.plantmate.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PlantEncyclopedia (
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val harvest: Int,
    @StringRes val difficult: Int
)