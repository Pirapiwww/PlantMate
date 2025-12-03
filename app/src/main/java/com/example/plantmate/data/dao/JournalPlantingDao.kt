package com.example.plantmate.data.dao

import androidx.room.*
import com.example.plantmate.model.JournalPlantingEntity

@Dao
interface JournalPlantingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planting: JournalPlantingEntity)

    @Update
    suspend fun update(planting: JournalPlantingEntity)

    @Delete
    suspend fun delete(planting: JournalPlantingEntity)

    @Query("SELECT * FROM journal_planting ORDER BY createdAt DESC")
    suspend fun getAll(): List<JournalPlantingEntity>

    @Query("SELECT * FROM journal_planting WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): JournalPlantingEntity?
}
