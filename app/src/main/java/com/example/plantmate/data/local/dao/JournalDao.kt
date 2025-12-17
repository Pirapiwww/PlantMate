package com.example.plantmate.data.local.dao

import androidx.room.*
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(journal: JournalEntity): Long

    @Query("SELECT * FROM journal ORDER BY createdDate DESC")
    fun getAll(): Flow<List<JournalEntity>>

    @Query("SELECT * FROM journal WHERE id = :journalId LIMIT 1")
    suspend fun getById(journalId: Int): JournalEntity?

    @Update
    suspend fun update(journal: JournalEntity)

    @Delete
    suspend fun delete(journal: JournalEntity)
}
