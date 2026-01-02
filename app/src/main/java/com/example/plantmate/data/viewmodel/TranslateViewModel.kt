package com.example.plantmate.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.Translation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TranslateViewModel : ViewModel() {

    private val _translatedSections = MutableStateFlow<List<String>>(emptyList())
    val translatedSections: StateFlow<List<String>> = _translatedSections

    // Fungsi translate list of texts ke targetLanguage
    fun translateSections(sections: List<String>, targetLanguage: String = "id") {
        viewModelScope.launch {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(targetLanguage)
                .build()

            val translator = Translation.getClient(options)

            // Download model jika belum ada
            translator.downloadModelIfNeeded()
                .addOnSuccessListener {
                    // Terjemahkan tiap section satu per satu
                    val translatedList = mutableListOf<String>()
                    sections.forEachIndexed { index, text ->
                        translator.translate(text)
                            .addOnSuccessListener { translatedText ->
                                // update list dengan hasil translate
                                // pastikan thread safe dengan copy list
                                val current = _translatedSections.value.toMutableList()
                                while (current.size <= index) current.add("")
                                current[index] = translatedText
                                _translatedSections.value = current
                            }
                            .addOnFailureListener {
                                // kalau gagal, tetap pakai teks asli
                                val current = _translatedSections.value.toMutableList()
                                while (current.size <= index) current.add("")
                                current[index] = text
                                _translatedSections.value = current
                            }
                    }
                }
                .addOnFailureListener {
                    // gagal download model, pakai teks asli
                    _translatedSections.value = sections
                }
        }
    }
}
