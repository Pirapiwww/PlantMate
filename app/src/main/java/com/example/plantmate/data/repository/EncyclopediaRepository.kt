package com.example.plantmate.data.repository

import com.example.plantmate.data.api.PerenualApiClient
import com.example.plantmate.data.local.dao.EncyclopediaDao
import com.example.plantmate.model.PlantDetail
import com.example.plantmate.model.PlantListItem
import com.example.plantmate.model.CareGuideResponse

class EncyclopediaRepository(encyclopediaDao: EncyclopediaDao) {

    private val key = "sk-c3l3692db5ae5db7313752"

    suspend fun searchPlants(query: String): List<PlantListItem> {
        if (query.isBlank()) return emptyList()

        val resp = PerenualApiClient.api.searchSpecies(
            apiKey = key,
            query = query
        )
        return resp.data
    }

    suspend fun getPlantDetail(id: Int): PlantDetail {
        return PerenualApiClient.api.getSpeciesDetails(
            id = id,
            apiKey = key
        )
    }

    suspend fun getPlantCareGuide(id: Int): CareGuideResponse {
        return PerenualApiClient.api.getSpeciesCareGuide(
            speciesId = id,
            apiKey = key
        )
    }

}
