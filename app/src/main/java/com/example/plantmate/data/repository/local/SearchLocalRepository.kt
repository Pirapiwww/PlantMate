package com.example.plantmate.data.repository.local

import com.example.plantmate.data.local.dao.SearchDao
import com.example.plantmate.data.local.entity.SearchEntity

class SearchLocalRepository(
    private val dao: SearchDao
) {

    val search = dao.getAllSearch()

    suspend fun addSearch(
        history: String
    ) {
        dao.insertSearch(
            SearchEntity(
                history = history
            )
        )
    }

    suspend fun deleteSearch(search: SearchEntity) {
        dao.deleteSearch(search)
    }


}
