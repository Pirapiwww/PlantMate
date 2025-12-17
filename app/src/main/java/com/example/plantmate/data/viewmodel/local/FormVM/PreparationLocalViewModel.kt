package com.example.plantmate.data.viewmodel.local.FormVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.FormEntity.PreparationEntity
import com.example.plantmate.data.repository.local.FormRepo.PreparationLocalRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreparationLocalViewModel(
    private val repo: PreparationLocalRepository
) : ViewModel() {

    /* ================= SELECTED (SINGLE) ================= */

    private val _selectedPreparation =
        MutableStateFlow<PreparationEntity?>(null)

    val selectedPreparation: StateFlow<PreparationEntity?> =
        _selectedPreparation

    fun loadById(id: Int) {
        viewModelScope.launch {
            _selectedPreparation.value = repo.getById(id)
        }
    }

    /* ================= LIST (ALL) ================= */

    val allPreparations: StateFlow<List<PreparationEntity>> =
        repo.allPreparations.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    /* ================= LIST BY JOURNAL ================= */

    private val _preparationsByJournalId =
        MutableStateFlow<Int?>(null)

    val preparationsByJournal: StateFlow<List<PreparationEntity>> =
        _preparationsByJournalId
            .filterNotNull()
            .flatMapLatest { journalId ->
                repo.getPreparationsByJournal(journalId)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun loadByJournalId(journalId: Int) {
        _preparationsByJournalId.value = journalId
    }

    /* ================= ADD (RETURN ID) ================= */

    fun addPreparation(
        preparation: PreparationEntity,
        onResult: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val id = repo.addPreparation(preparation)
            onResult(id)
        }
    }

    /* ================= ADD (LEGACY) ================= */

    fun addPreparation(
        journalId: Int,
        title: String,
        plantType: String,
        source: String,
        soilType: String,
        fertilizerType: String,
        note: String?,
        analysisAI: String,
        createdDate: String
    ) {
        viewModelScope.launch {
            repo.addPreparation(
                journalId,
                title,
                plantType,
                source,
                soilType,
                fertilizerType,
                note,
                analysisAI,
                createdDate
            )
        }
    }

    /* ================= UPDATE (RETURN ID) ================= */

    fun updatePreparation(
        preparation: PreparationEntity,
        onResult: (Long) -> Unit
    ) {
        viewModelScope.launch {
            repo.updatePreparation(preparation)
            onResult(preparation.id.toLong())
        }
    }

    /* ================= DELETE ================= */

    fun deletePreparation(preparation: PreparationEntity) {
        viewModelScope.launch {
            repo.deletePreparation(preparation)
        }
    }

    /* ================= UPDATE ANALYSIS ================= */

    fun updateAnalysisAI(analysisText: String) {
        viewModelScope.launch {
            _selectedPreparation.value?.let { current ->
                repo.updatePreparation(
                    current.copy(analysisAI = analysisText)
                )
            }
        }
    }
}
