package com.example.plantmate.data.repository.local

import com.example.plantmate.data.local.dao.LensDao
import com.example.plantmate.data.local.entity.LensEntity
import java.io.File

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

        // 🔥 HAPUS FILE IMAGE CAMERA
        lens.lensImage?.let { path ->
            try {
                // pastikan ini path file lokal
                if (path.startsWith("/")) {
                    val file = File(path)
                    if (file.exists()) {
                        file.delete()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 🔥 HAPUS DATA DI ROOM
        dao.deleteLens(lens)
    }
}
