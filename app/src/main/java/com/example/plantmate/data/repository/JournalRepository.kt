package com.example.plantmate.data.repository

import com.example.plantmate.data.database.PlantMateDatabase
import com.example.plantmate.model.JournalPlantingEntity
import com.example.plantmate.model.JournalPreparationEntity
import com.example.plantmate.model.JournalTreatmentEntity

class JournalRepository(
    private val db: PlantMateDatabase
) {
    // PREPARATION
    suspend fun addPreparation(data: JournalPreparationEntity) =
        db.preparationDao().insert(data)

    suspend fun updatePreparation(data: JournalPreparationEntity) =
        db.preparationDao().update(data.copy(updatedAt = System.currentTimeMillis()))

    suspend fun deletePreparation(data: JournalPreparationEntity) =
        db.preparationDao().delete(data)

    suspend fun allPreparation() = db.preparationDao().getAll()

    // PLANTING
    suspend fun addPlanting(data: JournalPlantingEntity) =
        db.plantingDao().insert(data)

    suspend fun updatePlanting(data: JournalPlantingEntity) =
        db.plantingDao().update(data.copy(updatedAt = System.currentTimeMillis()))

    suspend fun deletePlanting(data: JournalPlantingEntity) =
        db.plantingDao().delete(data)

    suspend fun allPlanting() = db.plantingDao().getAll()

    // TREATMENT
    suspend fun addTreatment(data: JournalTreatmentEntity) =
        db.treatmentDao().insert(data)

    suspend fun updateTreatment(data: JournalTreatmentEntity) =
        db.treatmentDao().update(data.copy(updatedAt = System.currentTimeMillis()))

    suspend fun deleteTreatment(data: JournalTreatmentEntity) =
        db.treatmentDao().delete(data)

    suspend fun allTreatment() = db.treatmentDao().getAll()
}
