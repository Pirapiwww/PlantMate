package com.example.plantmate.data.local.entity.FormEntity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.plantmate.data.local.entity.JournalEntity

@Entity(
    tableName = "treatment",
    foreignKeys = [
        ForeignKey(
            entity = JournalEntity::class,
            parentColumns = ["id"],
            childColumns = ["journalId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TreatmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val journalId: Int, // parent journal
    val title: String,
    val plantCondition: String,
    val treatmentType: String,
    val problem: String,
    val solution: String,
    val note: String?,
    val analysisAI: String,
    val createdDate: String
)

