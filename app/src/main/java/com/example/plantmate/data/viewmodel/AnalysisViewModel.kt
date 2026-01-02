package com.example.plantmate.data.viewmodel

import androidx.lifecycle.ViewModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnalysisViewModel : ViewModel() {

    // ✅ Model yang DIDUKUNG Android SDK
    private val generativeModel = GenerativeModel(
        modelName = "gemini-3-pro-preview",
        apiKey = Constants.apikey1
    )

    /**
     * @param formInput String gabungan dari
     *        Planting / Preparation / Treatment ViewModel
     * @param isIndonesianLanguage true = ID, false = EN
     */
    suspend fun sendAnalysis(
        formInput: String,
        isIndonesianLanguage: Boolean
    ): String = withContext(Dispatchers.IO) {

        val systemPrompt = if (isIndonesianLanguage) {
            // 🇮🇩 Indonesia
            """
            Kamu adalah asisten ahli pertanian dan perawatan tanaman.

            Tugas kamu adalah:
            1. Memeriksa apakah data tanaman dari pengguna sudah LENGKAP dan MASUK AKAL
            2. Menilai apakah cara perawatan sudah BENAR atau perlu diperbaiki
            3. Memberikan saran perbaikan jika ada kesalahan atau kekurangan
            4. Memberikan tips & trik tambahan agar tanaman tumbuh lebih sehat

            Jawaban WAJIB memiliki struktur berikut:
            1. Validasi Data
            2. Diagnosis Kondisi Tanaman
            3. Penyebab
            4. Solusi dan Perbaikan Perawatan
            5. Tips & Trik Tambahan

            Gunakan bahasa Indonesia yang sederhana dan mudah dipahami pemula.
            """.trimIndent()
        } else {
            // 🇬🇧 English
            """
            You are an expert assistant in agriculture and plant care.

            Your tasks:
            1. Validate whether the plant data is COMPLETE and LOGICAL
            2. Evaluate whether the care method is CORRECT
            3. Suggest corrections if needed
            4. Provide additional tips & tricks

            Your response MUST follow this structure:
            1. Data Validation
            2. Plant Condition Diagnosis
            3. Causes
            4. Treatment and Care Improvements
            5. Additional Tips & Tricks

            Use clear, beginner-friendly English.
            """.trimIndent()
        }

        val fullPrompt = """
            $systemPrompt

            User plant data:
            $formInput
        """.trimIndent()

        try {
            val response = generativeModel.generateContent(fullPrompt)

            response.text ?: if (isIndonesianLanguage)
                "Tidak ada respon dari AI"
            else
                "No response from AI"

        } catch (e: Exception) {
            if (isIndonesianLanguage)
                "Terjadi kesalahan: ${e.message}"
            else
                "An error occurred: ${e.message}"
        }
    }
}
