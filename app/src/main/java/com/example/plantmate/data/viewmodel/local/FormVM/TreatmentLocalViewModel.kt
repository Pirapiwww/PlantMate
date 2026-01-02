package com.example.plantmate.data.viewmodel.local.FormVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.local.entity.FormEntity.TreatmentEntity
import com.example.plantmate.data.repository.local.FormRepo.TreatmentLocalRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TreatmentLocalViewModel(
    private val repo: TreatmentLocalRepository
) : ViewModel() {

    /* ================= SELECTED (SINGLE) ================= */

    private val _selectedTreatment =
        MutableStateFlow<TreatmentEntity?>(null)

    val selectedTreatment: StateFlow<TreatmentEntity?> =
        _selectedTreatment

    fun loadById(id: Int) {
        viewModelScope.launch {
            _selectedTreatment.value = repo.getById(id)
        }
    }

    /* ================= LIST (ALL) ================= */

    val allTreatments: StateFlow<List<TreatmentEntity>> =
        repo.allTreatments.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    /* ================= LIST BY JOURNAL ================= */

    private val _treatmentsByJournalId =
        MutableStateFlow<Int?>(null)

    val treatmentsByJournal: StateFlow<List<TreatmentEntity>> =
        _treatmentsByJournalId
            .filterNotNull()
            .flatMapLatest { journalId ->
                repo.getTreatmentsByJournal(journalId)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun loadByJournalId(journalId: Int) {
        _treatmentsByJournalId.value = journalId
    }

    /* ================= ADD (RETURN ID) ================= */

    fun addTreatment(
        treatment: TreatmentEntity,
        onResult: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val id = repo.addTreatment(treatment)
            onResult(id)
        }
    }

    /* ================= ADD (LEGACY) ================= */

    fun addTreatment(
        journalId: Int,
        title: String,
        plantCondition: String,
        treatmentType: String,
        problem: String,
        solution: String,
        note: String?,
        analysisAI: String,
        createdDate: String
    ) {
        viewModelScope.launch {
            repo.addTreatment(
                journalId,
                title,
                plantCondition,
                treatmentType,
                problem,
                solution,
                note,
                analysisAI,
                createdDate
            )
        }
    }

    /* ================= UPDATE (RETURN ID) ================= */

    fun updateTreatment(
        treatment: TreatmentEntity,
        onResult: (Long) -> Unit
    ) {
        viewModelScope.launch {
            repo.updateTreatment(treatment)
            onResult(treatment.id.toLong())
        }
    }

    /* ================= DELETE ================= */

    fun deleteTreatment(treatment: TreatmentEntity) {
        viewModelScope.launch {
            repo.deleteTreatment(treatment)
        }
    }

    /* ================= UPDATE ANALYSIS ================= */

    fun updateAnalysisAI(analysisText: String) {
        viewModelScope.launch {
            _selectedTreatment.value?.let { current ->
                repo.updateTreatment(
                    current.copy(analysisAI = analysisText)
                )
            }
        }
    }

    fun getSelectedTreatmentAsString(
        isIndonesianLanguage: Boolean
    ): String? {
        val treatment = _selectedTreatment.value ?: return null

        return if (isIndonesianLanguage) {
            // 🇮🇩 Bahasa Indonesia
            buildString {
                appendLine("Judul: ${treatment.title}")
                appendLine("Kondisi tanaman: ${treatment.plantCondition}")
                appendLine("Jenis perawatan: ${treatment.treatmentType}")
                appendLine("Masalah: ${treatment.problem}")
                appendLine("Solusi: ${treatment.solution}")

                treatment.note?.takeIf { it.isNotBlank() }?.let {
                    appendLine("Catatan: $it")
                }

                appendLine("Tanggal dibuat: ${treatment.createdDate}")
            }
        } else {
            // 🇬🇧 English
            buildString {
                appendLine("Title: ${treatment.title}")
                appendLine("Plant condition: ${treatment.plantCondition}")
                appendLine("Treatment type: ${treatment.treatmentType}")
                appendLine("Problem: ${treatment.problem}")
                appendLine("Solution: ${treatment.solution}")

                treatment.note?.takeIf { it.isNotBlank() }?.let {
                    appendLine("Notes: $it")
                }

                appendLine("Created date: ${treatment.createdDate}")
            }
        }
    }

}
