package com.example.plantmate

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.plantmate.data.viewmodel.local.ViewModelFactory
import com.example.plantmate.navigation.NavbarApp
import com.example.plantmate.ui.theme.PlantMateTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// =========================================================================
// Nama Kelompok: ParaDX, Nama APlikasi: PlantMate
// Anggota:
// 1. Muhammad Rafi Rizqullah (23523222)
// 2. M. Nur Fadhil AS (23523233)
// 3. Muhammad Rizky Prayoga (23523225)
// =========================================================================

val LocalViewModelFactory = staticCompositionLocalOf<ViewModelProvider.Factory> {
    error("No ViewModelFactory provided")
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val app = application as YourApp

        val factory = ViewModelFactory(
            encyclopediaLocalRepository = app.encyclopediaLocalRepository,
            newsLocalRepository = app.newsLocalRepository,
            lensLocalRepository = app.lensLocalRepository,
            searchLocalRepository = app.searchLocalRepository,
            journalLocalRepository = app.journalLocalRepository,
            preparationLocalRepository = app.preparationLocalRepository,
            plantingLocalRepository = app.plantingLocalRepository,
            treatmentLocalRepository = app.treatmentLocalRepository
        )

        setContent {
            PlantMateTheme {
                CompositionLocalProvider(LocalViewModelFactory provides factory) {
                    val navController = rememberNavController()

                    // 🔹 Launch sync news ketika Composable pertama kali dibangun
                    LaunchedEffect(Unit) {
                        // Pastikan syncNews dijalankan di background
                        CoroutineScope(Dispatchers.IO).launch {
                            app.newsLocalRepository.syncNews()
                        }
                    }

                    NavbarApp(
                        navController = navController,
                        database = app.database
                    )
                }
            }
        }
    }
}
