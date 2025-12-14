package com.example.plantmate.data.local.dao

import androidx.room.*
import com.example.plantmate.data.local.entity.LensEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LensDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLens(lens: LensEntity)

    @Query("SELECT * FROM lens ORDER BY id DESC")
    fun getAllLens(): Flow<List<LensEntity>>

    @Delete
    suspend fun deleteLens(lens: LensEntity)

    @Query("SELECT * FROM lens ORDER BY id DESC")
    suspend fun getAllLensOnce(): List<LensEntity>

}
