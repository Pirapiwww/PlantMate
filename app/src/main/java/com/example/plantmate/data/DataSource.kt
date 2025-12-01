package com.example.plantmate.data

import com.example.plantmate.R
import com.example.plantmate.model.PlantNews
import com.example.plantmate.model.FeatureIcon
import com.example.plantmate.model.PlantEncyclopedia

class DataSource {


    fun loadNavbar(): List<FeatureIcon>{
        return listOf<FeatureIcon>(
            FeatureIcon(R.drawable.home, R.string.navbar_home),
            FeatureIcon(R.drawable.journal, R.string.navbar_journal),
            FeatureIcon(R.drawable.bookmark,R.string.navbar_bookmark),
            FeatureIcon(R.drawable.profile, R.string.navbar_profile)
        )
    }

    fun loadFeature(): List<FeatureIcon>{
        return listOf<FeatureIcon>(
            FeatureIcon(R.drawable.plant_journal, R.string.plant_journal),
            FeatureIcon(R.drawable.lens, R.string.plant_lens),
            FeatureIcon(R.drawable.encyclopedia,R.string.plant_encyclopedia),
            FeatureIcon(R.drawable.news, R.string.plant_news)
        )
    }

    fun loadEncyclopedia(): List<PlantEncyclopedia>{
        return listOf<PlantEncyclopedia>(
            PlantEncyclopedia(R.drawable.bayam_merah, R.string.encyclopedia_title, R.string.encyclopedia_harvest, R.string.encyclopedia_difficult)
        )
    }
}