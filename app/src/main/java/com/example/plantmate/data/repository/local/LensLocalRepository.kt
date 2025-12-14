package com.example.plantmate.data.repository.local

import android.net.Uri
import com.example.plantmate.data.local.dao.LensDao
import com.example.plantmate.data.local.dao.NewsDao
import com.example.plantmate.data.local.entity.EncyclopediaEntity
import com.example.plantmate.data.local.entity.LensEntity
import com.example.plantmate.data.local.entity.NewsEntity
import com.example.plantmate.data.repository.NewsRepository

class LensLocalRepository(
    private val dao: LensDao
) {

    val lens = dao.getAllLens()

    suspend fun addLens(
        lensImage: String?,
        title: String?,
        result: String?,
        savedDate: String?
    ) {
        dao.insertLens(
            LensEntity(
                lensImage = lensImage,
                title = title,
                result = result,
                savedDate = savedDate
            )
        )
    }

    suspend fun deleteLens(lens: LensEntity) {
        dao.deleteLens(lens)
    }
}
