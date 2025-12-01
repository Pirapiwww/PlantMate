package com.example.plantmate.data.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.repository.fetchNews
import com.example.plantmate.model.PlantNews
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    var newsList by mutableStateOf<List<PlantNews>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            try {
                newsList = fetchNews()
            } finally {
                isLoading = false
            }
        }
    }
}

