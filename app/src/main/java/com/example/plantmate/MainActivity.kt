package com.example.plantmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.plantmate.data.viewmodel.local.ViewModelFactory
import com.example.plantmate.navigation.NavbarApp
import com.example.plantmate.ui.theme.PlantMateTheme

val LocalViewModelFactory = staticCompositionLocalOf<ViewModelProvider.Factory> {
    error("No ViewModelFactory provided")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Agar Compose bisa pakai seluruh screen (tanpa inset otomatis)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Controller untuk mengatur visibility sistem bar
        val insetsController =
            WindowInsetsControllerCompat(window, window.decorView).apply {

                // 🟢 Hide NAVIGATION BAR saja
                hide(WindowInsetsCompat.Type.navigationBars())

                // Mode immersive — swipe untuk muncul sementara
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

        val app = application as YourApp

        val factory = ViewModelFactory(
            encyclopediaLocalRepository = app.encyclopediaLocalRepository,
            newsLocalRepository = app.newsLocalRepository,
            lensLocalRepository = app.lensLocalRepository
            )

        setContent {
            PlantMateTheme {
                CompositionLocalProvider(LocalViewModelFactory provides factory) {
                    val navController = rememberNavController()

                    NavbarApp(
                        navController = navController,
                        database = app.database
                    )
                }
            }
        }
    }
}
