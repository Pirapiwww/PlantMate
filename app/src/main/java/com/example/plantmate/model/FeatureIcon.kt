package com.example.plantmate.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FeatureIcon (
    @DrawableRes val featureImage: Int,
    @StringRes val featureTitle: Int
)