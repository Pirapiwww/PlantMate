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
import com.example.plantmate.data.viewmodel.local.LensLocalViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlantLensResultScreen(
    imageUri: String?,
    onBack: () -> Unit,
    navController: NavHostController
) {

    var title by rememberSaveable { mutableStateOf("") }
    var titleError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val app = context.applicationContext as YourApp

    val viewModel: LensLocalViewModel =
        viewModel(factory = app.viewModelFactory)

    val analysisText = """
        Status Kesehatan Tanaman: 🌱 Sehat (Probabilitas: 0.92)

        Kemungkinan Penyakit:
            - Leaf Spot (Prob: 0.18)
            - Powdery Mildew (Prob: 0.09)

        Klasifikasi Tumbuhan:
            - Capsicum annuum (Prob: 0.87)
            - Solanum lycopersicum (Prob: 0.12)
    """.trimIndent()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ================= APP BAR =================
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
                            if (file.exists()) file.delete()
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
                text = stringResource(id = R.string.plant_lens),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        // ================= CONTENT =================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.lens_image),
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
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.lens_result),
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
                    Text(text = analysisText)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.title),
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
                label = { Text("${stringResource(id = R.string.title)} *") },
                placeholder = { Text(stringResource(id = R.string.lens_ex)) },
                isError = titleError,
                singleLine = true
            )

            if (titleError) {
                Text(
                    text = stringResource(id = R.string.lens_title),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                        return@Button
                    }

                    val savedDate = SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale("id", "ID")
                    ).format(Date())

                    viewModel.addLens(
                        lensImage = imageUri,
                        title = title,
                        result = analysisText,
                        savedDate = savedDate
                    )

                    navController.navigate("bookmark?tab=0") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.navbar_bookmark))
            }
        }
    }
}
