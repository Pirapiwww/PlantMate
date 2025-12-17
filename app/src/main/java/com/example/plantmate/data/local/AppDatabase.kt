package com.example.plantmate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantmate.data.local.dao.*
import com.example.plantmate.data.local.dao.FormDao.PlantingDao
import com.example.plantmate.data.local.dao.FormDao.PreparationDao
import com.example.plantmate.data.local.dao.FormDao.TreatmentDao
import com.example.plantmate.data.local.entity.JournalEntity
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.local.entity.FormEntity.TreatmentEntity
import com.example.plantmate.data.local.entity.EncyclopediaEntity
import com.example.plantmate.data.local.entity.LensEntity
import com.example.plantmate.data.local.entity.NewsEntity
import com.example.plantmate.data.local.entity.SearchEntity

@Database(
    entities = [
        JournalEntity::class,
        PreparationEntity::class,
        PlantingEntity::class,
        TreatmentEntity::class,
        EncyclopediaEntity::class,
        NewsEntity::class,
        LensEntity::class,
        SearchEntity::class
    ],
    version = 9,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAO baru
    abstract fun journalDao(): JournalDao
    abstract fun preparationDao(): PreparationDao
    abstract fun plantingDao(): PlantingDao
    abstract fun treatmentDao(): TreatmentDao

    // DAO lama
    abstract fun encyclopediaDao(): EncyclopediaDao
    abstract fun newsDao(): NewsDao
    abstract fun lensDao(): LensDao
    abstract fun searchDao(): SearchDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "plantmate.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
