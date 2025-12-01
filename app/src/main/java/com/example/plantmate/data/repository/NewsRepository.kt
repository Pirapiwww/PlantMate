package com.example.plantmate.data.repository

import com.example.plantmate.model.PlantNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

suspend fun fetchNews(): List<PlantNews> {
    val url = "https://news.google.com/rss/search?q=tanaman+when:7d&hl=id&gl=ID&ceid=ID:id"

    return withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(url).get()
        val items = doc.select("item")

        items.map { item ->
            val title = item.select("title").text()
            val link = item.select("link").text()
            val pubDate = item.select("pubDate").text()
            val sourceRaw = item.select("source").text()
            val source = cleanSourceUrl(sourceRaw)

            // Fetch OG image dari halaman berita
            val realUrl = extractRealUrl(link)
            val ogImage = fetchOgImage(realUrl)

            PlantNews(
                newsImage = ogImage,
                source = source,
                newsDate = pubDate,
                newsTitle = title,
                pubLink = link
            )
        }
    }
}

fun cleanSourceUrl(url: String): String {
    // Hilangkan protokol
    var clean = url.removePrefix("https://")
        .removePrefix("http://")

    // Hilangkan www.
    clean = clean.removePrefix("www.")

    // Ambil hanya nama domain pertama (sebelum titik)
    clean = clean.substringBefore(".")

    // Ubah ke huruf besar
    return clean.uppercase()
}

fun extractRealUrl(googleNewsUrl: String): String {
    // Google News kadang mengandung "url="
    return try {
        val url = java.net.URL(googleNewsUrl)
        val query = url.query ?: return googleNewsUrl

        val params = query.split("&")
        for (p in params) {
            if (p.startsWith("url=")) {
                return p.removePrefix("url=").trim()
            }
        }
        googleNewsUrl
    } catch (e: Exception) {
        googleNewsUrl
    }
}



