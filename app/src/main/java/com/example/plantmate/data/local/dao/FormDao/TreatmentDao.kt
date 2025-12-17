package com.example.plantmate.data.local.dao.FormDao

import androidx.room.*
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity
import com.example.plantmate.data.local.entity.FormEntity.TreatmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TreatmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(treatment: TreatmentEntity): Long

    @Query("SELECT * FROM treatment ORDER BY createdDate DESC")
    fun getAll(): Flow<List<TreatmentEntity>>

    @Query("SELECT * FROM treatment WHERE journalId = :journalId ORDER BY createdDate DESC")
    fun getByJournal(journalId: Int): Flow<List<TreatmentEntity>>

    @Delete
    suspend fun delete(treatment: TreatmentEntity)

    @Update
    suspend fun update(treatment: TreatmentEntity)

    @Query("SELECT * FROM treatment WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TreatmentEntity?
}
