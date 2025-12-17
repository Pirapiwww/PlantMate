package com.example.plantmate.data.local.dao.FormDao

import androidx.room.*
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(planting: PlantingEntity): Long

    @Query("SELECT * FROM planting ORDER BY createdDate DESC")
    fun getAll(): Flow<List<PlantingEntity>>

    @Query("SELECT * FROM planting WHERE journalId = :journalId ORDER BY createdDate DESC")
    fun getByJournal(journalId: Int): Flow<List<PlantingEntity>>

    @Delete
    suspend fun delete(planting: PlantingEntity)

    @Update
    suspend fun update(planting: PlantingEntity)

    @Query("SELECT * FROM planting WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): PlantingEntity?
}
