package com.example.plantmate.data.viewmodel

import androidx.lifecycle.ViewModel
import com.example.plantmate.GroqApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class AnalysisViewModel : ViewModel() {

    private val apiKey = GroqApiKey.API_KEY
    private val client = OkHttpClient()

    suspend fun sendAnalysis(
        formInput: String,
        isIndonesianLanguage: Boolean
    ): String = withContext(Dispatchers.IO) {

        val systemPrompt = if (isIndonesianLanguage) {
            """
            Kamu adalah asisten AI ahli pertanian dan perawatan tanaman.
        
            ATURAN WAJIB:
            - Jangan menampilkan reasoning, proses berpikir, atau analisis internal
            - Jangan mengomentari kelengkapan atau kekurangan data
            - Jangan menebak data yang tidak disebutkan
            - Jangan menulis teks di luar JSON
            - Gunakan bahasa Indonesia sederhana
            - Output HARUS JSON VALID
        
            DEFINISI VALIDASI DATA (WAJIB DIPATUHI):
            - Validasi Data adalah pengecekan KESESUAIAN informasi dengan tanaman yang dimaksud
            - Periksa apakah:
              - Nama tanaman pada judul sesuai dengan karakteristik tanaman tersebut
              - Jenis tanaman (buah, sayur, herba, dll) sesuai dengan isinya
              - Tahap kegiatan pada judul sesuai dengan pembahasan
            - Jika data TIDAK sesuai:
              - Nyatakan "Data tidak sesuai"
              - Jelaskan penyebab ketidaksesuaian secara singkat dan jelas
            - Jika data sesuai:
              - Nyatakan "Data sesuai"
              - Jelaskan alasannya
            - Jangan menilai kelengkapan data
            - Jangan menyebut data kurang atau tidak lengkap
        
            ATURAN LIST (WAJIB DIPATUHI):
            - "B. Solusi dan Perbaikan" dan "C. Tips & Trik":
              - Gunakan penomoran 1, 2, 3, 4, 5, dan seterusnya
              - TULISKAN SEMUA poin relevan
              - JANGAN membatasi jumlah poin berdasarkan contoh
        
            CARA ANALISIS:
            - Tentukan jenis analisis HANYA berdasarkan JUDUL
            - Jangan menggunakan asumsi lain
        
            ATURAN BERDASARKAN JUDUL:
            1. Jika judul mengandung "Persiapan Penanaman":
               - Fokus pada persiapan sebelum tanam
            2. Jika judul mengandung "Penanaman":
               - Fokus pada proses penanaman
            3. Jika judul mengandung "Perawatan":
               - Fokus pada perawatan rutin tanaman
        
            STRUKTUR JSON WAJIB:
            {
              "A. Validasi Data": "Kesimpulan kesesuaian data beserta alasannya",
              "B. Solusi dan Perbaikan": [
                "1. Solusi atau perbaikan relevan pertama",
                "2. Solusi atau perbaikan relevan kedua",
                "3. Solusi atau perbaikan relevan ketiga",
                "dan seterusnya sesuai kebutuhan"
              ],
              "C. Tips & Trik": [
                "1. Tips atau trik relevan pertama",
                "2. Tips atau trik relevan kedua",
                "3. Tips atau trik relevan ketiga",
                "dan seterusnya sesuai kebutuhan"
              ]
            }
        
            DATA TANAMAN (TERMASUK JUDUL):
            $formInput
            """.trimIndent()
                } else {
                    """
            You are an AI assistant specialized in agriculture and plant care.
        
            MANDATORY RULES:
            - Do NOT show reasoning, chain-of-thought, or internal analysis
            - Do NOT comment on data completeness or missing information
            - Do NOT guess or assume any unspecified data
            - Do NOT write any text outside JSON
            - Use simple and clear English
            - Output MUST be valid JSON only
        
            DATA VALIDATION DEFINITION (MUST FOLLOW):
            - Data Validation means checking CONSISTENCY with the described plant
            - Verify whether:
              - The plant name in the title matches the plant characteristics
              - The plant type (fruit, vegetable, herb, etc.) matches the content
              - The activity stage in the title matches the discussion
            - If the data is NOT suitable:
              - Explicitly state "Data not suitable"
              - Clearly explain the reason
            - If the data is suitable:
              - Explicitly state "Data suitable"
              - Clearly explain the reason
            - Do NOT evaluate data completeness
            - Do NOT mention missing or insufficient data
        
            LIST RULES (MUST FOLLOW):
            - "B. Solution and Improvement" and "C. Tips & Tricks":
              - Use numbering 1, 2, 3, 4, 5, and so on
              - INCLUDE ALL relevant points
              - DO NOT limit the number of items based on examples
        
            ANALYSIS METHOD:
            - Determine the analysis type ONLY based on the TITLE
            - Do NOT use any assumptions
        
            TITLE-BASED RULES:
            1. If the title contains "Planting Preparation":
               - Focus on pre-planting preparation
            2. If the title contains "Planting":
               - Focus on the planting process
            3. If the title contains "Care" or "Treatment":
               - Focus on routine plant care
        
            REQUIRED JSON STRUCTURE:
            {
              "A. Data Validation": "Data suitability conclusion with clear reason",
              "B. Solution and Improvement": [
                "1. First relevant solution or improvement",
                "2. Second relevant solution or improvement",
                "3. Third relevant solution or improvement",
                "and so on as needed"
              ],
              "C. Tips & Tricks": [
                "1. First relevant tip or trick",
                "2. Second relevant tip or trick",
                "3. Third relevant tip or trick",
                "and so on as needed"
              ]
            }
        
            PLANT DATA (INCLUDING TITLE):
            $formInput
            """.trimIndent()
        }


        try {
            val jsonBody = JSONObject().apply {
                put("model", "openai/gpt-oss-20b")
                put("input", systemPrompt)
            }

            val requestBody = jsonBody
                .toString()
                .toRequestBody("application/json; charset=utf-8".toMediaType())

            val request = Request.Builder()
                .url("https://api.groq.com/openai/v1/responses")
                .addHeader("Authorization", "Bearer $apiKey")
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                val rawBody = response.body?.string() ?: return@withContext "Empty response"

                val json = JSONObject(rawBody)
                val outputArray = json.optJSONArray("output") ?: return@withContext "No output"

                for (i in 0 until outputArray.length()) {
                    val block = outputArray.getJSONObject(i)
                    val contentArray = block.optJSONArray("content") ?: continue

                    for (j in 0 until contentArray.length()) {
                        val content = contentArray.getJSONObject(j)
                        if (content.optString("type") == "output_text") {
                            return@withContext formatAiJsonToReadableText(
                                content.optString("text"),
                                isIndonesianLanguage
                            )
                        }
                    }
                }
                "AI tidak mengembalikan output"
            }

        } catch (e: Exception) {
            "Exception: ${e.message}"
        }
    }

    /* ===== FORMAT OUTPUT (RAPI, TANPA TAB BERLEBIHAN) ===== */
    private fun formatAiJsonToReadableText(
        jsonText: String,
        isIndonesianLanguage: Boolean
    ): String {
        return try {
            val clean = jsonText
                .replace("```json", "")
                .replace("```", "")
                .trim()

            val json = JSONObject(clean)

            val titleA = if (isIndonesianLanguage) "A. Validasi Data" else "A. Data Validation"
            val titleB = if (isIndonesianLanguage) "B. Solusi dan Perbaikan" else "B. Solution and Improvement"
            val titleC = if (isIndonesianLanguage) "C. Tips & Trik" else "C. Tips & Tricks"

            fun formatArray(arr: org.json.JSONArray?): String {
                if (arr == null || arr.length() == 0) return "-"
                return (0 until arr.length())
                    .joinToString("\n") { arr.getString(it) }
            }

            buildString {
                appendLine(titleA)
                appendLine()
                appendLine(json.optString(titleA))
                appendLine()
                appendLine(titleB)
                appendLine()
                appendLine(formatArray(json.optJSONArray(titleB)))
                appendLine()
                appendLine(titleC)
                appendLine()
                appendLine(formatArray(json.optJSONArray(titleC)))
            }.trim()

        } catch (e: Exception) {
            jsonText
        }
    }
}
