package com.example.plantmate.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PlantNews (
    @DrawableRes val newsImage: Int,
    @StringRes val source: Int,
    @StringRes val newsDate: Int,
    @StringRes val newsTitle: Int)