package com.example.plantmate.data.repository.local

import com.example.plantmate.data.local.dao.JournalDao
import com.example.plantmate.data.local.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

class JournalLocalRepository(
    private val dao: JournalDao
) {

    val allJournals: Flow<List<JournalEntity>> = dao.getAll()

    suspend fun getById(id: Int): JournalEntity? {
        return dao.getById(id)
    }

    suspend fun addJournal(journal: JournalEntity): Long {
        return dao.insert(journal)
    }

    suspend fun addJournal(
        plantName: String,
        createdDate: String
    ): Long {
        return dao.insert(
            JournalEntity(
                plantName = plantName,
                createdDate = createdDate
            )
        )
    }

    suspend fun updateJournal(journal: JournalEntity) {
        dao.update(journal)
    }

    suspend fun deleteJournal(journal: JournalEntity) {
        dao.delete(journal)
    }
}
