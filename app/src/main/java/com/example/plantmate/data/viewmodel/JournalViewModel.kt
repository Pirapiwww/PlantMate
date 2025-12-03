package com.example.plantmate.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.repository.JournalRepository
import com.example.plantmate.model.JournalPlantingEntity
import com.example.plantmate.model.JournalPreparationEntity
import com.example.plantmate.model.JournalTreatmentEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JournalViewModel(
    private val repo: JournalRepository
) : ViewModel() {

    // STATE
    private val _preparationList = MutableStateFlow<List<JournalPreparationEntity>>(emptyList())
    val preparationList: StateFlow<List<JournalPreparationEntity>> = _preparationList

    private val _plantingList = MutableStateFlow<List<JournalPlantingEntity>>(emptyList())
    val plantingList: StateFlow<List<JournalPlantingEntity>> = _plantingList

    private val _treatmentList = MutableStateFlow<List<JournalTreatmentEntity>>(emptyList())
    val treatmentList: StateFlow<List<JournalTreatmentEntity>> = _treatmentList

    // LOAD ALL
    fun loadAll() {
        viewModelScope.launch {
            _preparationList.value = repo.allPreparation()
            _plantingList.value = repo.allPlanting()
            _treatmentList.value = repo.allTreatment()
        }
    }

    // CRUD BASIC
    fun insertPreparation(data: JournalPreparationEntity) = viewModelScope.launch {
        repo.addPreparation(data)
        loadAll()
    }

    fun updatePreparation(data: JournalPreparationEntity) = viewModelScope.launch {
        repo.updatePreparation(data)
        loadAll()
    }

    fun deletePreparation(data: JournalPreparationEntity) = viewModelScope.launch {
        repo.deletePreparation(data)
        loadAll()
    }

    fun insertPlanting(data: JournalPlantingEntity) = viewModelScope.launch {
        repo.addPlanting(data)
        loadAll()
    }

    fun updatePlanting(data: JournalPlantingEntity) = viewModelScope.launch {
        repo.updatePlanting(data)
        loadAll()
    }

    fun deletePlanting(data: JournalPlantingEntity) = viewModelScope.launch {
        repo.deletePlanting(data)
        loadAll()
    }

    fun insertTreatment(data: JournalTreatmentEntity) = viewModelScope.launch {
        repo.addTreatment(data)
        loadAll()
    }

    fun updateTreatment(data: JournalTreatmentEntity) = viewModelScope.launch {
        repo.updateTreatment(data)
        loadAll()
    }

    fun deleteTreatment(data: JournalTreatmentEntity) = viewModelScope.launch {
        repo.deleteTreatment(data)
        loadAll()
    }
}
