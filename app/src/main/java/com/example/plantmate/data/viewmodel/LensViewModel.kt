package com.example.plantmate.data.viewmodel

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.api.PlantIdApiProvider
import com.example.plantmate.model.LensAnalysisResult
import com.example.plantmate.model.PlantIdRequest
import com.example.plantmate.model.PlantIdResponse
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class LensViewModel : ViewModel() {

    // ================= ML KIT =================
    private val _analysisResult =
        MutableStateFlow<List<LensAnalysisResult>>(emptyList())
    val analysisResult: StateFlow<List<LensAnalysisResult>> = _analysisResult

    private val _loadingLabeling = MutableStateFlow(false)
    val loadingLabeling: StateFlow<Boolean> = _loadingLabeling

    private val labeler = ImageLabeling.getClient(
        ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.4f)
            .build()
    )

    // ================= PLANT.ID =================
    private val _result = MutableStateFlow<PlantIdResponse?>(null)
    val result: StateFlow<PlantIdResponse?> = _result

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loadingML = MutableStateFlow(false)
    val loadingML: StateFlow<Boolean> = _loadingML

    private val plantApi = PlantIdApiProvider.api

    // ================= ML KIT LABELING =================
    fun analyzeBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            _loadingLabeling.value = true
            try {
                val image = InputImage.fromBitmap(bitmap, 0)
                val labels = labeler.process(image).await()

                _analysisResult.value = labels.map {
                    LensAnalysisResult(it.text, it.confidence)
                }
            } catch (e: Exception) {
                Log.e("MLKit", e.message ?: "ML Kit error")
                _analysisResult.value = emptyList()
            } finally {
                _loadingLabeling.value = false
            }
        }
    }

    // ================= PLANT.ID ANALYSIS =================
    fun analyzePlant(bitmap: Bitmap) {
        viewModelScope.launch {
            _loadingML.value = true
            _error.value = null

            try {
                val resized = resizeBitmap(bitmap, 1024)
                val base64 = bitmapToBase64(resized)

                val request = PlantIdRequest(
                    images = listOf(base64),
                    classification_level = "all",
                    health = "auto",
                    disease_model = "full"
                )

                val response = plantApi.identifyPlant(request)

                if (response.isSuccessful) {
                    _result.value = response.body()
                } else {
                    _error.value =
                        "Error ${response.code()}: ${response.errorBody()?.string()}"
                }

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loadingML.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        labeler.close()
    }
}

// ================= UTIL =================

private fun bitmapToBase64(bitmap: Bitmap): String {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
    return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
}

fun resizeBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
    val ratio = minOf(
        maxSize.toFloat() / bitmap.width,
        maxSize.toFloat() / bitmap.height
    )
    return Bitmap.createScaledBitmap(
        bitmap,
        (bitmap.width * ratio).toInt(),
        (bitmap.height * ratio).toInt(),
        true
    )
}
