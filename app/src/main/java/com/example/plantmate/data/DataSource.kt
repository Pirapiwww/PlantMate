package com.example.plantmate.data

import com.example.plantmate.R
import com.example.plantmate.model.PlantNews
import com.example.plantmate.model.FeatureIcon

class DataSource {

    fun loadNews(): List<PlantNews> {
        return listOf<PlantNews>(
            PlantNews(R.drawable.img_news1, R.string.news_source1, R.string.news_date1, R.string.news_title1),
            PlantNews(R.drawable.img_news2, R.string.news_source2, R.string.news_date2, R.string.news_title2)

        )
    }

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
}