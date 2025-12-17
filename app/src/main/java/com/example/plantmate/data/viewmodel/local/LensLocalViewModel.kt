package com.example.plantmate.data.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.LensEntity
import com.example.plantmate.data.repository.local.LensLocalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LensLocalViewModel(
    private val repo: LensLocalRepository
) : ViewModel() {

    val lens = repo.lens.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun addLens(
        lensImage: String?,
        title: String?,
        result: String?,
        savedDate: String?
    ) {
        viewModelScope.launch {
            repo.addLens(
                lensImage = lensImage,
                title = title,
                result = result,
                savedDate = savedDate
            )
        }
    }

    fun deleteLens(item: LensEntity) {
        viewModelScope.launch {
            repo.deleteLens(item)
        }
    }
}
