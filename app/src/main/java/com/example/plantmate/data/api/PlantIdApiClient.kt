package com.example.plantmate.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlantIdApiClient {
    private const val BASE_URL = "https://api.plant.id/v3/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
