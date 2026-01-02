package com.example.plantmate.ui.plantlens

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import com.example.plantmate.data.viewmodel.LensViewModel
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import com.example.plantmate.isIndonesianLanguage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantLensResultScreen(
    imageUri: String?,
    onBack: () -> Unit,
    navController: NavHostController
) {
    val context = LocalContext.current
    val app = context.applicationContext as YourApp

    val lensViewModel: LensViewModel = viewModel()
    val localViewModel: LensLocalViewModel = viewModel(factory = app.viewModelFactory)

    var title by rememberSaveable { mutableStateOf("") }
    var titleError by rememberSaveable { mutableStateOf(false) }
    var analysisText by remember { mutableStateOf("") }
    var isPlantImage by remember { mutableStateOf(false) }

    val labels by lensViewModel.analysisResult.collectAsState()
    val labelingLoading by lensViewModel.loadingLabeling.collectAsState()
    val plantResult by lensViewModel.result.collectAsState()
    val plantError by lensViewModel.error.collectAsState()
    val plantLoading by lensViewModel.loadingML.collectAsState()

    // ================= TRIGGER ANALYSIS =================
    LaunchedEffect(imageUri, labels) {
        if (imageUri == null) return@LaunchedEffect

        try {
            val bitmap = BitmapFactory.decodeFile(imageUri)
            if (bitmap == null) {
                analysisText = if (isIndonesianLanguage()) {
                    "❌ Gagal membaca gambar"
                } else {
                    "❌ Failed to read image"
                }
                isPlantImage = false
                return@LaunchedEffect
            }

            // ML Kit
            lensViewModel.analyzeBitmap(bitmap)

            // cek plant-related label >=40%
            val plantLabels = labels.filter {
                val l = it.label.lowercase()
                l.contains("plant") || l.contains("vegetable") ||
                        l.contains("fruit") || l.contains("flower")
            }

            val maxPlantConfidence: Float = (plantLabels.maxOfOrNull { it.confidence } ?: 0f) * 100f

            if (maxPlantConfidence >= 40f) {
                isPlantImage = true
                lensViewModel.analyzePlant(bitmap)
            } else {
                isPlantImage = false
                analysisText = if (isIndonesianLanguage()) {
                    "❌ Bukan gambar tanaman atau gambar buram, silakan ambil gambar lagi"
                } else {
                    "❌ Not a plant image or blurry, please take another picture"
                }
            }

        } catch (e: Exception) {
            analysisText = if (isIndonesianLanguage()) {
                "❌ Error membaca gambar:\n${e.message}"
            } else {
                "❌ Error reading image:\n${e.message}"
            }
            isPlantImage = false
        }
    }

    // ================= UPDATE ANALYSIS TEXT =================
    LaunchedEffect(plantResult, plantError, plantLoading, labels) {
        if (plantLoading || !isPlantImage) return@LaunchedEffect

        plantError?.let {
            analysisText = if (isIndonesianLanguage()) {
                "❌ Gagal analisis:\n$it"
            } else {
                "❌ Analysis failed:\n$it"
            }
            return@LaunchedEffect
        }

        plantResult?.result?.let { result ->
            analysisText = buildString {
                val isiID = isIndonesianLanguage()

                append(if (isiID) "🌱 Status Kesehatan:\n" else "🌱 Health Status:\n")
                val health = result.is_healthy
                if (health == null) append(if (isiID) "Tidak diketahui\n\n" else "Unknown\n\n")
                else {
                    val status = when (health.binary) {
                        true -> if (isiID) "Sehat" else "Healthy"
                        false -> if (isiID) "Tidak Sehat" else "Unhealthy"
                        else -> if (isiID) "Tidak diketahui" else "Unknown"
                    }
                    val percent = ((health.probability ?: 0.0) * 100).toInt()
                    append("$status ($percent%)\n\n")
                }

                append(if (isiID) "🦠 Penyakit:\n" else "🦠 Diseases:\n")
                val diseases = result.diseases
                if (diseases.isNullOrEmpty()) append(if (isiID) "Tidak ditemukan\n\n" else "None found\n\n")
                else {
                    diseases.forEach { disease ->
                        val percent = ((disease.probability ?: 0.0) * 100).toInt()
                        append("- ${disease.name ?: "-"} ($percent%)\n")
                    }
                    append("\n")
                }

                append(if (isiID) "🌿 Klasifikasi Tanaman:\n" else "🌿 Plant Classification:\n")
                val species = result.classification?.suggested_species
                if (species.isNullOrEmpty()) append(if (isiID) "Tidak dapat ditentukan\n" else "Cannot be determined\n")
                else {
                    species.take(3).forEach { s ->
                        val percent = ((s.probability ?: 0.0) * 100).toInt()
                        append("- ${s.name ?: "-"} ($percent%)\n")
                    }
                }

                val insectLabels = labels.filter { it.label.lowercase().contains("insect") }
                if (insectLabels.isNotEmpty()) {
                    val maxConfidence = (insectLabels.maxOf { it.confidence } * 100).toInt()
                    append("\n${if (isiID) "🐞 Terindikasi adanya insect" else "🐞 Insect detected"} ($maxConfidence%)\n")
                }
            }
        }
    }

    // ================= UI =================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // APP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDDE6C7))
                .padding(top = 38.dp, bottom = 16.dp, start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }

            Text(
                text = stringResource(id = R.string.plant_lens ),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // TITLE GAMBAR
            Text(
                text = stringResource(id = R.string.lens_image),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // GAMBAR
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Box(Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // TITLE LABELS
            Text(
                text = stringResource(id = R.string.labelling),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // ML KIT LABELS
            if (labelingLoading) {
                CircularProgressIndicator()
            } else {
                Row(Modifier.horizontalScroll(rememberScrollState())) {
                    labels.forEach { label ->
                        Box(
                            modifier = Modifier
                                .background(color = Color(0xFFDDE6C7), shape = MaterialTheme.shapes.small)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${label.label} ${(label.confidence * 100).toInt()}%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // TITLE HASIL ANALISIS
            Text(
                text = stringResource(id = R.string.lens_result),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // SURFACE ANALISIS - wrapContentHeight agar memanjang
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.medium
            ) {
                Box(Modifier.padding(16.dp)) {
                    if (plantLoading) CircularProgressIndicator()
                    else Text(analysisText)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Hanya tampil kalau gambar tanaman valid
            if (isPlantImage) {
                Text(
                    text = stringResource(id = R.string.title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = it.isBlank()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Judul *") },
                    isError = titleError
                )

                Spacer(Modifier.height(12.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        enabled = title.isNotBlank(),
                        onClick = {
                            val date = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(Date())
                            localViewModel.addLens(imageUri, title, analysisText, date)
                            navController.navigate("bookmark?tab=0") { popUpTo(0) { inclusive = true } }
                        }
                    ) {
                        Text(stringResource(id = R.string.bookmark))
                    }
                }

                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
