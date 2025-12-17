package com.example.plantmate.data.local.entity.FormEntity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.plantmate.data.local.entity.JournalEntity

@Entity(
    tableName = "planting",
    foreignKeys = [
        ForeignKey(
            entity = JournalEntity::class,
            parentColumns = ["id"],
            childColumns = ["journalId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlantingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val journalId: Int,
    val title: String,
    val method: String,
    val location: String,
    val frequency: String,
    val amount: String,
    val note: String?,
    val analysisAI: String,
    val createdDate: String
)
