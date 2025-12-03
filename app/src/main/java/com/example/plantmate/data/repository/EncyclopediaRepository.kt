package com.example.plantmate.data.repository

import com.example.plantmate.data.api.ApiClient
import com.example.plantmate.model.PlantDetail
import com.example.plantmate.model.PlantListItem
import com.example.plantmate.model.CareGuideResponse

class EncyclopediaRepository {

    private val key = "sk-c3l3692db5ae5db7313752"

    suspend fun searchPlants(query: String): List<PlantListItem> {
        if (query.isBlank()) return emptyList()

        val resp = ApiClient.api.searchSpecies(
            apiKey = key,
            query = query
        )
        return resp.data
    }

    suspend fun getPlantDetail(id: Int): PlantDetail {
        return ApiClient.api.getSpeciesDetails(
            id = id,
            apiKey = key
        )
    }

    suspend fun getPlantCareGuide(id: Int): CareGuideResponse {
        return ApiClient.api.getSpeciesCareGuide(
            speciesId = id,
            apiKey = key
        )
    }

}
