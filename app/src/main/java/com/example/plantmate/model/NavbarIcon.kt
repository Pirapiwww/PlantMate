package com.example.plantmate.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavbarIcon (
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val route: String
)