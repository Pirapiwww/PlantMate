package com.example.plantmate.data.repository

import org.jsoup.Jsoup

suspend fun fetchOgImage(url: String): String? {
    return try {
        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)") // penting!
            .timeout(10_000)
            .get()

        // Urutan prioritas
        val ogImage =
            doc.select("meta[property=og:image]").attr("content").ifBlank { null }
                ?: doc.select("meta[property=og:image:url]").attr("content").ifBlank { null }
                ?: doc.select("meta[name=twitter:image]").attr("content").ifBlank { null }
                ?: doc.select("meta[name=twitter:image:src]").attr("content").ifBlank { null }

        ogImage
    } catch (e: Exception) {
        null
    }
}
