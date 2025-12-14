package com.example.plantmate.data.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantmate.data.repository.local.EncyclopediaLocalRepository
import com.example.plantmate.data.repository.local.LensLocalRepository
import com.example.plantmate.data.repository.local.NewsLocalRepository


class ViewModelFactory(
    private val encyclopediaLocalRepository: EncyclopediaLocalRepository,
    private val newsLocalRepository: NewsLocalRepository,
    private val lensLocalRepository: LensLocalRepository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EncyclopediaLocalViewModel::class.java) -> {
                EncyclopediaLocalViewModel(encyclopediaLocalRepository) as T
            }

            modelClass.isAssignableFrom(NewsLocalViewModel::class.java) -> {
                NewsLocalViewModel(newsLocalRepository) as T
            }

            modelClass.isAssignableFrom(LensLocalViewModel::class.java) -> {
                LensLocalViewModel(lensLocalRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}


