package com.example.plantmate.data.viewmodel.local.FormVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.FormEntity.PlantingEntity
import com.example.plantmate.data.repository.local.FormRepo.PlantingLocalRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlantingLocalViewModel(
    private val repo: PlantingLocalRepository
) : ViewModel() {

    /* ================= SELECTED (SINGLE) ================= */

    private val _selectedPlanting =
        MutableStateFlow<PlantingEntity?>(null)

    val selectedPlanting: StateFlow<PlantingEntity?> =
        _selectedPlanting

    fun loadById(id: Int) {
        viewModelScope.launch {
            _selectedPlanting.value = repo.getById(id)
        }
    }

    /* ================= LIST (ALL) ================= */

    val planting: StateFlow<List<PlantingEntity>> =
        repo.allPlantings.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    /* ================= LIST BY JOURNAL ================= */

    private val _plantingsByJournalId =
        MutableStateFlow<Int?>(null)

    val plantingsByJournal: StateFlow<List<PlantingEntity>> =
        _plantingsByJournalId
            .filterNotNull()
            .flatMapLatest { journalId ->
                repo.getPlantingsByJournal(journalId)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun loadByJournalId(journalId: Int) {
        _plantingsByJournalId.value = journalId
    }

    /* ================= ADD (RETURN ID) ================= */

    fun addPlanting(
        planting: PlantingEntity,
        onResult: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val id = repo.addPlanting(planting)
            onResult(id)
        }
    }

    /* ================= ADD (LEGACY, OPTIONAL) ================= */

    fun addPlanting(
        journalId: Int,
        title: String,
        method: String,
        location: String,
        frequency: String,
        amount: String,
        note: String?,
        analysisAI: String,
        createdDate: String
    ) {
        viewModelScope.launch {
            repo.addPlanting(
                journalId,
                title,
                method,
                location,
                frequency,
                amount,
                note,
                analysisAI,
                createdDate
            )
        }
    }

    /* ================= UPDATE (RETURN ID) ================= */

    fun updatePlanting(
        planting: PlantingEntity,
        onResult: (Long) -> Unit
    ) {
        viewModelScope.launch {
            repo.updatePlanting(planting)
            onResult(planting.id.toLong())
        }
    }

    /* ================= DELETE ================= */

    fun deletePlanting(planting: PlantingEntity) {
        viewModelScope.launch {
            repo.deletePlanting(planting)
        }
    }

    /* ================= UPDATE ANALYSIS ================= */

    fun updateAnalysisAI(
        analysisText: String
    ) {
        viewModelScope.launch {
            _selectedPlanting.value?.let { current ->
                repo.updatePlanting(
                    current.copy(analysisAI = analysisText)
                )
            }
        }
    }
}
