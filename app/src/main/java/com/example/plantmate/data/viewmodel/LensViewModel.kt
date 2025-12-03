package com.example.plantmate.data.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantmate.data.api.PlantIdApi
import com.example.plantmate.data.api.PlantIdApiClient
import com.example.plantmate.model.PlantIdRequest
import com.example.plantmate.model.PlantIdResponse
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class LensViewModel : ViewModel() {

    private val api = PlantIdApiClient.retrofit.create(PlantIdApi::class.java)

    var result by mutableStateOf<PlantIdResponse?>(null)
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun analyzePlant(bitmap: Bitmap) {
        viewModelScope.launch {
            try {
                loading = true
                error = null

                val base64 = bitmapToBase64(bitmap)

                // ⬇ REQUEST FINAL TANPA ERROR 400
                val request = PlantIdRequest(
                    api_key = "YOUR_API_KEY",
                    images = listOf(base64),
                    modifiers = listOf(
                        "classification_level=all",
                        "health=all",
                        "similar_images=true",
                        "symptoms=true"
                    )
                )

                val response = api.identifyPlant(request)

                if (response.isSuccessful) {
                    result = response.body()
                } else {
                    error = "Error ${response.code()}: ${response.errorBody()?.string()}"
                }

            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
}

fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun uriToBase64(context: Context, uri: Uri): String {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: ByteArray(0)
        Base64.encodeToString(bytes, Base64.NO_WRAP)
    } catch (e: Exception) {
        ""
    }
}