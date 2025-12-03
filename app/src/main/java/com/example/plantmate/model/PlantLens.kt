package com.example.plantmate.model

data class PlantIdRequest(
    val api_key: String,
    val images: List<String>,
    val modifiers: List<String>? = null
)

data class PlantIdResponse(
    val result: PlantResult?
)

data class PlantResult(
    val is_healthy: HealthStatus?,
    val diseases: List<PlantDisease>?,
    val classification: Classification?
)

data class HealthStatus(
    val binary: Boolean?,
    val probability: Double?
)

data class Classification(
    val suggested_species: List<SuggestedSpecies>?
)

data class SuggestedSpecies(
    val name: String?,
    val probability: Double?
)

data class PlantDisease(
    val name: String?,
    val probability: Double?
)