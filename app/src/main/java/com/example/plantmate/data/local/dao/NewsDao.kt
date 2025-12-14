package com.example.plantmate.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.plantmate.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    // ===== INSERT =====
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsList: List<NewsEntity>)

    // ===== SELECT =====
    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news ORDER BY id DESC")
    suspend fun getAllNewsOnce(): List<NewsEntity>

    // ===== DELETE =====
    @Delete
    suspend fun deleteNews(news: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun deleteAll()
}
