package com.example.plantmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "encyclopedia")
data class EncyclopediaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUrl: String? = null,
    val commonName: String?,
    val scientificName: String?,
    val sunlightDesc: String?,
    val wateringDesc: String?,
    val pruningDesc: String?
)
