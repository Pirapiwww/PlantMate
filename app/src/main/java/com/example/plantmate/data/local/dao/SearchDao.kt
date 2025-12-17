package com.example.plantmate.data.local.dao

import androidx.room.*
import com.example.plantmate.data.local.entity.LensEntity
import com.example.plantmate.data.local.entity.SearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: SearchEntity)

    @Query("SELECT * FROM search ORDER BY id DESC")
    fun getAllSearch(): Flow<List<SearchEntity>>

    @Delete
    suspend fun deleteSearch(search: SearchEntity)

}
