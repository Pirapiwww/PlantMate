package com.example.plantmate.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import com.example.plantmate.data.repository.EncyclopediaRepository
import com.example.plantmate.model.CareGuideItem
import com.example.plantmate.model.PlantListItem
import com.example.plantmate.model.PlantDetail

class EncyclopediaViewModel(private val repo: EncyclopediaRepository): ViewModel() {

    private val _query = MutableStateFlow("")
    fun setQuery(q: String) { _query.value = q }

    // UI states
    private val _results = MutableStateFlow<List<PlantListItem>>(emptyList())
    val results: StateFlow<List<PlantListItem>> = _results.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        _query
            .debounce(400)
            .distinctUntilChanged()
            .onEach { q ->
                _error.value = null
                if (q.isBlank()) {
                    _results.value = emptyList()
                    return@onEach
                }
                _isLoading.value = true
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val list = repo.searchPlants(q)
                        _results.value = list
                    } catch (e: Exception) {
                        _error.value = e.localizedMessage ?: "Terjadi kesalahan"
                    } finally {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
    }

    // DETAIL
    fun loadDetail(id: Int, onResult: (PlantDetail?) -> Unit) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val detail = repo.getPlantDetail(id)
                onResult(detail)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Gagal ambil detail"
                onResult(null)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadCareGuide(id: Int, onResult: (List<CareGuideItem>?) -> Unit) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getPlantCareGuide(id)
                onResult(response.data)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Gagal ambil Care Guide"
                onResult(null)
            } finally {
                _isLoading.value = false
            }
        }
    }

}
