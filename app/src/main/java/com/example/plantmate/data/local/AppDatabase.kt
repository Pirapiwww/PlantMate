package com.example.plantmate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantmate.data.local.dao.EncyclopediaDao
import com.example.plantmate.data.local.dao.LensDao
import com.example.plantmate.data.local.dao.NewsDao
import com.example.plantmate.data.local.entity.EncyclopediaEntity
import com.example.plantmate.data.local.entity.LensEntity
import com.example.plantmate.data.local.entity.NewsEntity

@Database(
    entities = [
        EncyclopediaEntity::class,
        NewsEntity::class,
        LensEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun encyclopediaDao(): EncyclopediaDao
    abstract fun newsDao(): NewsDao
    abstract fun lensDao(): LensDao

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
                    // 🔥 INI YANG PENTING
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
