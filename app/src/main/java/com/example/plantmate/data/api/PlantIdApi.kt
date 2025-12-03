package com.example.plantmate.data.api

import com.example.plantmate.model.PlantIdRequest
import com.example.plantmate.model.PlantIdResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface PlantIdApi {
    @POST("health_assessment")
    suspend fun identifyPlant(
        @Body request: PlantIdRequest
    ): Response<PlantIdResponse>
}

