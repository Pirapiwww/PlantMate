package com.example.plantmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val newsImage: String,
    val source: String,
    val newsDate: String,
    val newsTitle: String,
    val pubLink: String
)
