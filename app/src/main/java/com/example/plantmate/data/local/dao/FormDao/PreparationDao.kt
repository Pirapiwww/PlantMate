package com.example.plantmate.data.local.dao.FormDao

import androidx.room.*
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PreparationDao {

    // Insert preparation baru dan kembalikan ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preparation: PreparationEntity): Long

    // Ambil semua preparation
    @Query("SELECT * FROM preparation ORDER BY createdDate DESC")
    fun getAll(): Flow<List<PreparationEntity>>

    // Ambil semua preparation milik jurnal tertentu
    @Query("SELECT * FROM preparation WHERE journalId = :journalId ORDER BY createdDate DESC")
    fun getByJournal(journalId: Int): Flow<List<PreparationEntity>>

    // Hapus preparation tertentu
    @Delete
    suspend fun delete(preparation: PreparationEntity)

    // Update preparation
    @Update
    suspend fun update(preparation: PreparationEntity)

    @Query("SELECT * FROM preparation WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): PreparationEntity?
}
