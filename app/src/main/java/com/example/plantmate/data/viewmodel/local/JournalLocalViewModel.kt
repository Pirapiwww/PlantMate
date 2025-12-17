package com.example.plantmate.data.viewmodel.local.FormVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.local.entity.JournalEntity
import com.example.plantmate.data.repository.local.JournalLocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalLocalViewModel(
    private val repo: JournalLocalRepository
) : ViewModel() {

    private val _selectedJournal =
        MutableStateFlow<JournalEntity?>(null)

    val selectedJournal: StateFlow<JournalEntity?> =
        _selectedJournal

    fun loadJournalById(id: Int) {
        viewModelScope.launch {
            _selectedJournal.value = repo.getById(id)
        }
    }

    val allJournals = repo.allJournals.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun addJournal(title: String, createdDate: String) {
        viewModelScope.launch {
            repo.addJournal(title, createdDate)
        }
    }

    fun updateJournal(journal: JournalEntity) {
        viewModelScope.launch {
            repo.updateJournal(journal)
        }
    }

    fun deleteJournal(journal: JournalEntity) {
        viewModelScope.launch {
            repo.deleteJournal(journal)
        }
    }

}
