package com.example.plantmate.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_preparation")
data class JournalPreparationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phase: String,
    val date: String,
    val plantName: String,
    val plantType: String,
    val media: String,
    val note: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "journal_planting")
data class JournalPlantingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phase: String,
    val seeds: String,
    val date: String,
    val how: String,
    val conditions: String,
    val note: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "journal_treatment")
data class JournalTreatmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phase: String,
    val date: String,
    val plantCondition: String,
    val watering: String,
    val fertilizer: String,
    val problem: String,
    val solution: String,
    val note: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)