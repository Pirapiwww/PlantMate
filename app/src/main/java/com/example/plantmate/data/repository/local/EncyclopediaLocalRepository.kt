package com.example.plantmate.data.repository.local

import com.example.plantmate.data.local.dao.EncyclopediaDao
import com.example.plantmate.data.local.entity.EncyclopediaEntity
import kotlinx.coroutines.flow.Flow

class EncyclopediaLocalRepository(
    private val dao: EncyclopediaDao
) {

    val encyclopedia = dao.getAllPlants()

    suspend fun addEncyclopedia(
        commonName: String?,
        scientificName: String?,
        sunlightDesc: String?,
        wateringDesc: String?,
        pruningDesc: String?,
        imageUrl: String? = null
    ) {
        dao.insertEncyclopedia(
            EncyclopediaEntity(
                imageUrl = imageUrl,
                commonName = commonName,
                scientificName = scientificName,
                sunlightDesc = sunlightDesc,
                wateringDesc = wateringDesc,
                pruningDesc = pruningDesc
            )
        )
    }

    suspend fun deleteEncyclopedia(encyclopedia: EncyclopediaEntity) {
        dao.deleteEncyclopedia(encyclopedia)
    }

    fun isExist(commonName: String): Flow<Boolean> {
        return dao.isExist(commonName)
    }

}
