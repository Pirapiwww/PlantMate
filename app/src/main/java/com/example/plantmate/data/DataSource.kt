package com.example.plantmate.data

import com.example.plantmate.R
import com.example.plantmate.model.PlantNews

class DataSource {

    fun loadNews(): List<PlantNews> {
        return listOf<PlantNews>(
            PlantNews(R.drawable.img_news1, R.string.news_source1, R.string.news_date1, R.string.news_title1),
            PlantNews(R.drawable.img_news2, R.string.news_source2, R.string.news_date2, R.string.news_title2)

        )
    }

    fun loadIcons(){

    }


}