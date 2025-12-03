package com.example.plantmate.data.api

object ApiProvider {
    val plantIdApi: PlantIdApi by lazy {
        PlantIdApiClient.retrofit.create(PlantIdApi::class.java)
    }
}
