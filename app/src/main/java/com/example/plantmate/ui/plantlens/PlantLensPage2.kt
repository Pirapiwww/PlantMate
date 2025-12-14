package com.example.plantmate.ui.plantlens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.plantmate.R
import com.example.plantmate.YourApp
import com.example.plantmate.data.api.PlantIdApi
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import com.example.plantmate.data.viewmodel.local.ViewModelFactory
import com.example.plantmate.data.viewmodel.uriToBase64
import com.example.plantmate.model.PlantIdRequest
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantLensResultScreen(
    imageUri: String?,
    apiKey: String,
    plantIdApi: PlantIdApi,
    onBack: () -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var analysisText by remember { mutableStateOf("Menunggu analisis...") }
    var isLoading by remember { mutableStateOf(false) }

    var title by rememberSaveable { mutableStateOf("") }
    var titleError by rememberSaveable { mutableStateOf(false) }

    val viewModel: LensLocalViewModel = viewModel(
        factory = ViewModelFactory(
            (context.applicationContext as YourApp).encyclopediaLocalRepository,
            (context.applicationContext as YourApp).newsLocalRepository,
            (context.applicationContext as YourApp).lensLocalRepository
        )
    )

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            isLoading = true

            val base64 = uriToBase64(context, Uri.parse(imageUri))

            val request = PlantIdRequest(
                api_key = apiKey,
                images = listOf(base64)
            )

            scope.launch {
                try {
                    val response = plantIdApi.identifyPlant(request)

                    if (response.isSuccessful) {
                        val result = response.body()?.result

                        analysisText = buildString {

                            if (result == null) {
                                append("Tidak ada hasil analisis.\n")
                                return@buildString
                            }

                            // ========== HEALTH STATUS ==========
                            val isHealthy = result.is_healthy?.binary
                            val healthProb = result.is_healthy?.probability

                            append("Status Kesehatan Tanaman:\n")

                            when (isHealthy) {
                                true -> append("🌱 Sehat (Probabilitas: ${
                                    String.format("%.2f", healthProb ?: 0.0)
                                })\n\n")

                                false -> append("⚠ Tidak Sehat (Probabilitas: ${
                                    String.format("%.2f", healthProb ?: 0.0)
                                })\n\n")

                                null -> append("Tidak dapat menentukan kesehatan.\n\n")
                            }

                            // ========== DISEASE LIST ==========
                            if (result.diseases.isNullOrEmpty()) {
                                append("Tidak ditemukan penyakit.\n\n")
                            } else {
                                append("Kemungkinan Penyakit:\n")
                                result.diseases.forEach {
                                    append("- ${it.name ?: "Unknown"} (Prob: ${
                                        String.format("%.2f", it.probability ?: 0.0)
                                    })\n")
                                }
                                append("\n")
                            }

                            // ========== CLASSIFICATION ==========
                            val species = result.classification?.suggested_species

                            append("Klasifikasi Tumbuhan:\n")
                            if (species.isNullOrEmpty()) {
                                append("Tidak dapat mengidentifikasi spesies.")
                            } else {
                                species.take(3).forEach {
                                    append("- ${it.name ?: "Unknown"} (Prob: ${
                                        String.format("%.2f", it.probability ?: 0.0)
                                    })\n")
                                }
                            }
                        }
                    } else {
                        analysisText = "Gagal menganalisis. (HTTP ${response.code()})\n${response.errorBody()?.string()}"
                    }

                } catch (e: Exception) {
                    analysisText = "Gagal menganalisis gambar.\n${e.message}"
                }

                isLoading = false
            }
        }
    }

    // =================== UI =====================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // AppBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    imageUri?.let {
                        try {
                            val file = File(Uri.parse(it).path ?: "")
                            if (file.exists()) {
                                file.delete()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    onBack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }


            Spacer(modifier = Modifier.width(12.dp))

            Text(
                stringResource(id = R.string.plant_lens),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Gambar Bagian Tumbuhan",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Captured Plant Image",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Hasil Analisis AI",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(text = analysisText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Judul Catatan",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (it.isNotBlank()) titleError = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Judul Catatan *") },
                placeholder = { Text("Contoh: Daun cabai menguning") },
                isError = titleError,
                singleLine = true
            )

            if (titleError) {
                Text(
                    text = "Judul wajib diisi",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                        return@Button
                    }

                    val savedDate = SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()
                    ).format(Date())

                    viewModel.addLens(
                        lensImage = imageUri,
                        title = title,
                        result = analysisText,
                        savedDate = savedDate
                    )

                    navController.navigate("bookmark?tab=0") {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Bookmark")
            }

        }
    }
}