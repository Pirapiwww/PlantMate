package com.example.plantmate.data.repository.local.FormRepo

import com.example.plantmate.data.local.dao.FormDao.PreparationDao
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity
import kotlinx.coroutines.flow.Flow

class PreparationLocalRepository(
    private val dao: PreparationDao
) {

    // Ambil semua preparation
    val allPreparations: Flow<List<PreparationEntity>> = dao.getAll()

    // Ambil semua preparation milik jurnal tertentu
    fun getPreparationsByJournal(journalId: Int): Flow<List<PreparationEntity>> {
        return dao.getByJournal(journalId)
    }

    // Insert preparation baru
    suspend fun addPreparation(preparation: PreparationEntity): Long {
        return dao.insert(preparation)
    }

    suspend fun getById(id: Int): PreparationEntity? {
        return dao.getById(id)
    }

    // Overload insert dengan parameter field langsung
    suspend fun addPreparation(
        journalId: Int,
        title: String,
        plantType: String,
        source: String,
        soilType: String,
        fertilizerType: String,
        note: String?,
        analysisAI: String,
        createdDate: String
    ) {
        val prep = PreparationEntity(
            journalId = journalId,
            title = title,
            plantType = plantType,
            source = source,
            soilType = soilType,
            fertilizerType = fertilizerType,
            note = note,
            analysisAI = analysisAI,
            createdDate = createdDate
        )
        dao.insert(prep)
    }

    // Update preparation
    suspend fun updatePreparation(preparation: PreparationEntity) {
        dao.update(preparation)
    }

    // Hapus preparation
    suspend fun deletePreparation(preparation: PreparationEntity) {
        dao.delete(preparation)
    }
}
