package com.example.plantmate.model

import com.google.gson.annotations.SerializedName

data class PlantListResponse(
    val data: List<PlantListItem> = emptyList()
)

data class PlantListItem(
    val id: Int,
    val common_name: String?,
    val scientific_name: List<String>?,
    val default_image: DefaultImage?
)

data class DefaultImage(
    val thumbnail: String?,
    val medium_url: String?,
    val regular_url: String?
)

// Detail model (yang akan kita tampilkan di UI)
data class PlantDetail(
    val id: Int,
    val common_name: String?,
    val scientific_name: List<String>?,
    val family: String?,
    val description: String?,
    val watering: String?,
    val sunlight: String?,
    val soil: String?,
    val edible_leaf: Boolean?,
    val medicinal: String?,
    val poisonous_to_humans: Boolean?,
    val default_image: DefaultImage?,
    val care_guides: String?
)

data class CareGuideResponse(
    val data: List<CareGuideItem>,
    val to: Int?,
    val per_page: Int?,
    val current_page: Int?,
    val from: Int?,
    val last_page: Int?,
    val total: Int?
)

data class CareGuideItem(
    val id: Int?,
    val species_id: Int?,
    val common_name: String?,
    val scientific_name: List<String>?,
    val section: List<CareGuideSection>?
)

data class CareGuideSection(
    val id: Int?,
    val type: String?,        // "watering", "sunlight", "pruning"
    val description: String?
)

