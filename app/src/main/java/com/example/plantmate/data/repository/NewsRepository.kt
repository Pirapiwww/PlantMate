package com.example.plantmate.data.repository

import com.example.plantmate.data.local.dao.NewsDao
import com.example.plantmate.data.local.entity.NewsEntity
import com.example.plantmate.isIndonesianLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class NewsRepository(
    private val newsDao: NewsDao
) {

    // ===============================
    // SYNC NEWS
    // ===============================
    suspend fun syncNews() = withContext(Dispatchers.IO) {
        val newNewsList = fetchAllNews()
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

    // ===============================
    // FETCH SEMUA KATEGORI
    // ===============================
    private suspend fun fetchAllNews(): List<NewsEntity> {

        val keywords = if (isIndonesianLanguage()) {
            listOf("tanaman", "buah", "sayur", "tanaman hias", "herbal")
        } else {
            listOf("plants", "fruit", "vegetable", "ornamental plants", "herbs")
        }

        val urls = keywords.map { buildUrl(it) }

        val allNews = mutableListOf<NewsEntity>()

        urls.forEach { url ->
            allNews += fetchNewsFromNetwork(url)
        }

        // 🔥 HILANGKAN DUPLIKAT BERDASARKAN LINK
        return allNews.distinctBy { it.pubLink }
    }

    // ===============================
    // FETCH PER RSS
    // ===============================
    private suspend fun fetchNewsFromNetwork(url: String): List<NewsEntity> {
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
                val newsImage = "news_$randomNumber"

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

    // ===============================
    // HELPER
    // ===============================
    private fun buildUrl(keyword: String): String {
        val query = keyword.replace(" ", "+")
        val lang = if (isIndonesianLanguage()) "id" else "en"
        return "https://news.google.com/rss/search?q=$query+when:7d&hl=$lang&gl=ID&ceid=ID:$lang"
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
