package com.example.plantmate.data.viewmodel.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantmate.data.repository.local.*
import com.example.plantmate.data.repository.local.FormRepo.*
import com.example.plantmate.data.viewmodel.local.FormVM.JournalLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PlantingLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.PreparationLocalViewModel
import com.example.plantmate.data.viewmodel.local.FormVM.TreatmentLocalViewModel

class ViewModelFactory(
    private val encyclopediaLocalRepository: EncyclopediaLocalRepository,
    private val newsLocalRepository: NewsLocalRepository,
    private val lensLocalRepository: LensLocalRepository,
    private val searchLocalRepository: SearchLocalRepository,
    private val journalLocalRepository: JournalLocalRepository,
    private val preparationLocalRepository: PreparationLocalRepository,
    private val plantingLocalRepository: PlantingLocalRepository,
    private val treatmentLocalRepository: TreatmentLocalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Repository lama
            modelClass.isAssignableFrom(EncyclopediaLocalViewModel::class.java) -> {
                EncyclopediaLocalViewModel(encyclopediaLocalRepository) as T
            }

            modelClass.isAssignableFrom(NewsLocalViewModel::class.java) -> {
                NewsLocalViewModel(newsLocalRepository) as T
            }

            modelClass.isAssignableFrom(LensLocalViewModel::class.java) -> {
                LensLocalViewModel(lensLocalRepository) as T
            }

            modelClass.isAssignableFrom(SearchLocalViewModel::class.java) -> {
                SearchLocalViewModel(searchLocalRepository) as T
            }

            // Repository baru
            modelClass.isAssignableFrom(JournalLocalViewModel::class.java) -> {
                JournalLocalViewModel(journalLocalRepository) as T
            }

            modelClass.isAssignableFrom(PreparationLocalViewModel::class.java) -> {
                PreparationLocalViewModel(preparationLocalRepository) as T
            }

            modelClass.isAssignableFrom(PlantingLocalViewModel::class.java) -> {
                PlantingLocalViewModel(plantingLocalRepository) as T
            }

            modelClass.isAssignableFrom(TreatmentLocalViewModel::class.java) -> {
                TreatmentLocalViewModel(treatmentLocalRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}
