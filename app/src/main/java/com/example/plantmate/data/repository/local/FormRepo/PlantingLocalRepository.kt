package com.example.plantmate.data.repository.local.FormRepo

import com.example.plantmate.data.local.dao.FormDao.PlantingDao
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import kotlinx.coroutines.flow.Flow

class PlantingLocalRepository(
    private val dao: PlantingDao
) {

    // Ambil semua planting
    val allPlantings: Flow<List<PlantingEntity>> = dao.getAll()

    // Ambil semua planting milik jurnal tertentu
    fun getPlantingsByJournal(journalId: Int): Flow<List<PlantingEntity>> {
        return dao.getByJournal(journalId)
    }

    // Insert planting baru
    suspend fun addPlanting(planting: PlantingEntity): Long {
        return dao.insert(planting)
    }

    suspend fun getById(id: Int): PlantingEntity? {
        return dao.getById(id)
    }

    // Overload insert dengan parameter field langsung
    suspend fun addPlanting(
        journalId: Int,
        title: String,
        method: String,
        location: String,
        frequency: String,
        amount: String,
        note: String?,
        analysisAI: String,
        createdDate: String
    ) {
        val planting = PlantingEntity(
            journalId = journalId,
            title = title,
            method = method,
            location = location,
            frequency = frequency,
            amount = amount,
            note = note,
            analysisAI = analysisAI,
            createdDate = createdDate
        )
        dao.insert(planting)
    }

    // Update planting
    suspend fun updatePlanting(planting: PlantingEntity) {
        dao.update(planting)
    }

    // Hapus planting
    suspend fun deletePlanting(planting: PlantingEntity) {
        dao.delete(planting)
    }
}
