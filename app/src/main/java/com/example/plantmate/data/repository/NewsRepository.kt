package com.example.plantmate.data.repository

import androidx.compose.runtime.remember
import com.example.plantmate.data.local.dao.NewsDao
import com.example.plantmate.data.local.entity.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class NewsRepository(
    private val newsDao: NewsDao
) {

    suspend fun syncNews() = withContext(Dispatchers.IO) {
        val newNewsList = fetchNewsFromNetwork()
        val oldNewsList = newsDao.getAllNewsOnce()

        if (oldNewsList.isEmpty()) {
            newsDao.insertAll(newNewsList)
            return@withContext
        }

        val oldLinks = oldNewsList.map { it.pubLink }.toSet()
        val newLinks = newNewsList.map { it.pubLink }.toSet()

        if (oldLinks == newLinks) return@withContext

        newsDao.deleteAll()
        newsDao.insertAll(newNewsList)
    }

    private suspend fun fetchNewsFromNetwork(): List<NewsEntity> {
        val url =
            "https://news.google.com/rss/search?q=tanaman+when:7d&hl=id&gl=ID&ceid=ID:id"

        return withContext(Dispatchers.IO) {
            val doc = Jsoup.connect(url).get()
            val items = doc.select("item")

            items.map {
                val title = it.select("title").text()
                val link = it.select("link").text()
                val pubDate = it.select("pubDate").text()
                val sourceRaw = it.select("source").text()
                val source = cleanSourceUrl(sourceRaw)

                val randomNumber = (1..9).random()
                val newsImage = "news_$randomNumber" // ⬅️ SELALU VALID

                NewsEntity(
                    newsImage = newsImage,
                    source = source,
                    newsDate = pubDate,
                    newsTitle = title,
                    pubLink = link
                )
            }
        }
    }

    private fun cleanSourceUrl(url: String): String {
        return url
            .removePrefix("https://")
            .removePrefix("http://")
            .removePrefix("www.")
            .substringBefore(".")
            .uppercase()
    }
}
