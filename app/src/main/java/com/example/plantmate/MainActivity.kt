package com.example.plantmate

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultLanguage = "en"
        setLanguage(defaultLanguage)

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

                    NavbarApp(
                        navController = navController,
                        database = app.database
                    )
                }
            }
        }
    }
}
