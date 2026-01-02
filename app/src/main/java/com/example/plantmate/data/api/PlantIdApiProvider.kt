package com.example.plantmate.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlantIdApiProvider {

    private const val BASE_URL = "https://plant.id/api/v3/"
    private const val API_KEY = "uen9oga29jDR7uD5tTaTdLpNNSCAYbXODf1LysY7QiPoMSdNrj"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Api-Key", API_KEY)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: PlantIdApi = retrofit.create(PlantIdApi::class.java)
}
