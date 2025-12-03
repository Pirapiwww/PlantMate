package com.example.plantmate.data.dao

import androidx.room.*
import com.example.plantmate.model.JournalPreparationEntity

@Dao
interface JournalPreparationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prep: JournalPreparationEntity)

    @Update
    suspend fun update(prep: JournalPreparationEntity)

    @Delete
    suspend fun delete(prep: JournalPreparationEntity)

    @Query("SELECT * FROM journal_preparation ORDER BY createdAt DESC")
    suspend fun getAll(): List<JournalPreparationEntity>

    @Query("SELECT * FROM journal_preparation WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): JournalPreparationEntity?
}