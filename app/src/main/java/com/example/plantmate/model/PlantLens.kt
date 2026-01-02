package com.example.plantmate.model

// ================= ID REQUEST =================
data class PlantIdRequest(
    val images: List<String>,
    val classification_level: String? = null,
    val health: String? = null,
    val disease_model: String? = null
)

// ================= MAIN RESPONSE =================
data class PlantIdResponse(
    val result: PlantResult?
)

data class PlantResult(
    val is_healthy: HealthStatus?,
    val diseases: List<PlantDisease>?,
    val classification: Classification?
)

// ================= HEALTH =================
data class HealthStatus(
    val binary: Boolean?,   // true = sehat, false = tidak sehat
    val probability: Double? // 0.0 - 1.0
)

// ================= DISEASE =================
data class PlantDisease(
    val name: String?,
    val probability: Double? // 0.0 - 1.0
)

// ================= CLASSIFICATION =================
data class Classification(
    val suggested_species: List<SuggestedSpecies>?
)

data class SuggestedSpecies(
    val name: String?,       // Nama ilmiah
    val probability: Double? // 0.0 - 1.0
)