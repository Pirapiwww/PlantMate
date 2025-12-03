package com.example.plantmate.data.dao

import androidx.room.*
import com.example.plantmate.model.JournalTreatmentEntity

@Dao
interface JournalTreatmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(treatment: JournalTreatmentEntity)

    @Update
    suspend fun update(treatment: JournalTreatmentEntity)

    @Delete
    suspend fun delete(treatment: JournalTreatmentEntity)

    @Query("SELECT * FROM journal_treatment ORDER BY createdAt DESC")
    suspend fun getAll(): List<JournalTreatmentEntity>

    @Query("SELECT * FROM journal_treatment WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): JournalTreatmentEntity?
}
