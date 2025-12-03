package com.example.plantmate.data.api

// PerenualApi.kt
import com.example.plantmate.model.CareGuideResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

import com.example.plantmate.model.PlantListResponse
import com.example.plantmate.model.PlantDetail

interface PerenualApi {
    @GET("species-list")
    suspend fun searchSpecies(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("page") page: Int? = 1
    ): PlantListResponse

    @GET("species/details/{id}")
    suspend fun getSpeciesDetails(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): PlantDetail

    @GET("species-care-guide-list")
    suspend fun getSpeciesCareGuide(
        @Query("key") apiKey: String,
        @Query("species_id") speciesId: Int
    ): CareGuideResponse

}
