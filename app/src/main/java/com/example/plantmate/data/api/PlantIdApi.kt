package com.example.plantmate.data.api

import com.example.plantmate.model.PlantIdRequest
import com.example.plantmate.model.PlantIdResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlantIdApi {

    @POST("identification")
    suspend fun identifyPlant(
        @Body request: PlantIdRequest
    ): Response<PlantIdResponse>
}
