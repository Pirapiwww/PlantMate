package com.example.plantmate.data.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.NewsEntity
import com.example.plantmate.data.repository.local.NewsLocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsLocalViewModel(
    private val repo: NewsLocalRepository
) : ViewModel() {

    // Flow untuk list berita
    val news = repo.news.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // Loading State
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        syncNews()
    }

    fun syncNews() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repo.syncNews()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addNews(
        newsImage: String,
        source: String,
        newsDate: String,
        newsTitle: String,
        pubLink: String
    ) {
        viewModelScope.launch {
            repo.addNews(
                newsImage = newsImage,
                source = source,
                newsDate = newsDate,
                newsTitle = newsTitle,
                pubLink = pubLink
            )
        }
    }

    fun deleteNews(item: NewsEntity) {
        viewModelScope.launch {
            repo.deleteNews(item)
        }
    }
}
