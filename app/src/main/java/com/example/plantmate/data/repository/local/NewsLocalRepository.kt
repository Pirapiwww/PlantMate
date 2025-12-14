package com.example.plantmate.data.repository.local

import com.example.plantmate.data.local.dao.NewsDao
import com.example.plantmate.data.local.entity.NewsEntity
import com.example.plantmate.data.repository.NewsRepository

class NewsLocalRepository(
    private val dao: NewsDao,
    private val remote: NewsRepository
) {

    val news = dao.getAllNews()

    suspend fun syncNews() {
        remote.syncNews()
    }

    suspend fun addNews(
        newsImage: String,
        source: String,
        newsDate: String,
        newsTitle: String,
        pubLink: String
    ) {
        val entity = NewsEntity(
            newsImage = newsImage,
            source = source,
            newsDate = newsDate,
            newsTitle = newsTitle,
            pubLink = pubLink
        )

        dao.insertNews(entity)
    }


    suspend fun deleteNews(item: NewsEntity) {
        dao.deleteNews(item)
    }
}
