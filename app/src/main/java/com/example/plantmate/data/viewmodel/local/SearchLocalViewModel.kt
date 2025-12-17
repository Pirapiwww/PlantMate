package com.example.plantmate.data.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.EncyclopediaEntity
import com.example.plantmate.data.local.entity.SearchEntity
import com.example.plantmate.data.repository.local.EncyclopediaLocalRepository
import com.example.plantmate.data.repository.local.SearchLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchLocalViewModel(
    private val repo: SearchLocalRepository
) : ViewModel() {

    val search = repo.search
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    fun addSearch(history: String) {
        viewModelScope.launch {
            repo.addSearch(history)
        }
    }

    fun deleteSearch(item: SearchEntity) {
        viewModelScope.launch {
            repo.deleteSearch(item)
        }
    }
}