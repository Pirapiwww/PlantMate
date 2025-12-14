package com.example.plantmate.data.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.EncyclopediaEntity
import com.example.plantmate.data.repository.local.EncyclopediaLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EncyclopediaLocalViewModel(
    private val repo: EncyclopediaLocalRepository
) : ViewModel() {

    // Collect daftar plant sebagai StateFlow
    val encyclopedia = repo.encyclopedia.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun addEncyclopedia(
        commonName: String?,
        scientificName: String?,
        sunlightDesc: String?,
        wateringDesc: String?,
        pruningDesc: String?,
        imageUrl: String? = null
    ) {
        viewModelScope.launch {
            repo.addEncyclopedia(
                commonName = commonName,
                scientificName = scientificName,
                sunlightDesc = sunlightDesc,
                wateringDesc = wateringDesc,
                pruningDesc = pruningDesc,
                imageUrl = imageUrl
            )
        }
    }

    fun deleteEncyclopedia(item: EncyclopediaEntity) {
        viewModelScope.launch {
            repo.deleteEncyclopedia(item)
        }
    }

    /**
     * Cek apakah data sudah di-bookmark
     */
    fun isBookmarked(commonName: String?): Flow<Boolean> {
        return if (commonName.isNullOrBlank()) {
            flowOf(false)
        } else {
            repo.isExist(commonName)
        }
    }
}
