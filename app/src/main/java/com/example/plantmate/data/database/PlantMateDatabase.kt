package com.example.plantmate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantmate.data.dao.JournalPlantingDao
import com.example.plantmate.data.dao.JournalPreparationDao
import com.example.plantmate.data.dao.JournalTreatmentDao
import com.example.plantmate.model.JournalPlantingEntity
import com.example.plantmate.model.JournalPreparationEntity
import com.example.plantmate.model.JournalTreatmentEntity

@Database(
    entities = [
        JournalPreparationEntity::class,
        JournalPlantingEntity::class,
        JournalTreatmentEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PlantMateDatabase : RoomDatabase() {

    abstract fun preparationDao(): JournalPreparationDao
    abstract fun plantingDao(): JournalPlantingDao
    abstract fun treatmentDao(): JournalTreatmentDao
}
