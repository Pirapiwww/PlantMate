package com.example.plantmate.data.api


object PlantIdApiProvider {
    val plantIdApi: PlantIdApi by lazy {
        PlantIdApiClient.retrofit.create(PlantIdApi::class.java)
    }
}