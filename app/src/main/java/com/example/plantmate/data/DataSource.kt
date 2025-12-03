package com.example.plantmate.data

import com.example.plantmate.R
import com.example.plantmate.model.NavbarIcon
import com.example.plantmate.model.FeatureIcon

class DataSource {


    fun loadNavbar(): List<NavbarIcon>{
        return listOf<NavbarIcon>(
            NavbarIcon(R.drawable.home, R.string.navbar_home, "home"),
            NavbarIcon(R.drawable.journal, R.string.navbar_journal, "journal"),
            NavbarIcon(R.drawable.bookmark,R.string.navbar_bookmark, "bookmark"),
            NavbarIcon(R.drawable.profile, R.string.navbar_profile, "profile")
        )
    }

    fun loadFeature(): List<FeatureIcon>{
        return listOf<FeatureIcon>(
            FeatureIcon(R.drawable.plant_journal, R.string.plant_journal, "plantJournal"),
            FeatureIcon(R.drawable.lens, R.string.plant_lens, "plantLens"),
            FeatureIcon(R.drawable.encyclopedia,R.string.plant_encyclopedia, "plantEncyclopedia"),
            FeatureIcon(R.drawable.news, R.string.plant_news, "plantNews")
        )
    }


}