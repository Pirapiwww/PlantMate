package com.example.plantmate.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantmate.data.repository.EncyclopediaRepository

class EncyclopediaViewModelFactory(
    private val repo: EncyclopediaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EncyclopediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EncyclopediaViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
