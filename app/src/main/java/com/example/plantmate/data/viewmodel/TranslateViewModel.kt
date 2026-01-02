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

    /**
     * Translate list of formatted text WITHOUT breaking spacing, tabs, or line breaks
     */
    fun translateSections(
        sections: List<String>,
        targetLanguage: String = TranslateLanguage.INDONESIAN
    ) {
        viewModelScope.launch {

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(targetLanguage)
                .build()

            val translator = Translation.getClient(options)

            translator.downloadModelIfNeeded()
                .addOnSuccessListener {

                    val result = MutableList(sections.size) { "" }

                    sections.forEachIndexed { sectionIndex, sectionText ->

                        // 🔹 SPLIT PER LINE (PENTING!)
                        val lines = sectionText.split("\n")

                        val translatedLines = MutableList(lines.size) { "" }

                        lines.forEachIndexed { lineIndex, line ->

                            // 🔸 Kalau baris kosong / cuma spasi → JANGAN translate
                            if (line.isBlank()) {
                                translatedLines[lineIndex] = line
                                return@forEachIndexed
                            }

                            // 🔸 Simpan indent (spasi/tab di awal)
                            val indent = line.takeWhile { it == ' ' || it == '\t' }
                            val content = line.dropWhile { it == ' ' || it == '\t' }

                            translator.translate(content)
                                .addOnSuccessListener { translatedText ->
                                    translatedLines[lineIndex] =
                                        indent + translatedText

                                    result[sectionIndex] = translatedLines.joinToString("\n")
                                    _translatedSections.value = result.toList()
                                }
                                .addOnFailureListener {
                                    translatedLines[lineIndex] = line
                                    result[sectionIndex] = translatedLines.joinToString("\n")
                                    _translatedSections.value = result.toList()
                                }
                        }
                    }
                }
                .addOnFailureListener {
                    // Jika gagal download model → pakai teks asli
                    _translatedSections.value = sections
                }
        }
    }
}
