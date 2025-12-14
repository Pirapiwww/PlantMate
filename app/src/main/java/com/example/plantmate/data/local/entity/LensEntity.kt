package com.example.plantmate.data.local.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lens")
data class LensEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lensImage: String?,
    val title: String?,
    val result: String?,
    val savedDate: String?
)
