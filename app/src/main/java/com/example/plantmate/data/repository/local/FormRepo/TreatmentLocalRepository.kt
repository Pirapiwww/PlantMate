package com.example.plantmate.data.repository.local.FormRepo

import com.example.plantmate.data.local.dao.FormDao.TreatmentDao
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.local.entity.FormEntity.TreatmentEntity
import kotlinx.coroutines.flow.Flow

class TreatmentLocalRepository(
    private val dao: TreatmentDao
) {

    // Ambil semua treatment
    val allTreatments: Flow<List<TreatmentEntity>> = dao.getAll()

    // Ambil semua treatment milik jurnal tertentu
    fun getTreatmentsByJournal(journalId: Int): Flow<List<TreatmentEntity>> {
        return dao.getByJournal(journalId)
    }

    // Insert treatment baru
    suspend fun addTreatment(treatment: TreatmentEntity): Long {
        return dao.insert(treatment)
    }

    suspend fun getById(id: Int): TreatmentEntity? {
        return dao.getById(id)
    }

    // Overload insert dengan parameter field langsung
    suspend fun addTreatment(
        journalId: Int,
        title: String,
        plantCondition: String,
        treatmentType: String,
        problem: String,
        solution: String,
        note: String?,
        analysisAI: String,
        createdDate: String
    ) {
        val treatment = TreatmentEntity(
            journalId = journalId,
            title = title,
            plantCondition = plantCondition,
            treatmentType = treatmentType,
            problem = problem,
            solution = solution,
            note = note,
            analysisAI = analysisAI,
            createdDate = createdDate
        )
        dao.insert(treatment)
    }

    // Update treatment
    suspend fun updateTreatment(treatment: TreatmentEntity) {
        dao.update(treatment)
    }

    // Hapus treatment
    suspend fun deleteTreatment(treatment: TreatmentEntity) {
        dao.delete(treatment)
    }
}
